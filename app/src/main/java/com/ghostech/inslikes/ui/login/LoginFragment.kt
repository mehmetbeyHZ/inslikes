package com.ghostech.inslikes.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ghostech.inslikes.BuildConfig
import com.ghostech.inslikes.ConnectionFailedActivity
import com.ghostech.inslikes.MainActivity
import com.ghostech.inslikes.R
import com.ghostech.inslikes.databinding.FragmentLoginBinding
import com.ghostech.inslikes.helpers.NetworkHelper
import com.ghostech.inslikes.security.ByteLevel
import com.ghostech.inslikes.service.MClient
import com.ghostech.inslikes.service.MService
import com.ghostech.inslikes.service.WClient
import com.ghostech.inslikes.service.WService
import com.ghostech.inslikes.storage.CookieStorage
import com.ghostech.inslikes.storage.UserStorage
import com.ghostech.inslikes.ui.dialogs.LoadingDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.URLDecoder
import java.net.URLEncoder

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var userStorage: UserStorage
    private var compositeDisposable : CompositeDisposable? = null
    private lateinit var cookieStorage : CookieStorage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        userStorage = UserStorage(requireContext())
        compositeDisposable = CompositeDisposable()
        cookieStorage = CookieStorage(requireContext())
        cookieStorage.clearDB()
        CookieManager.getInstance().removeAllCookies(null)
        loadAuthentication()
        return _binding!!.root
    }

    @SuppressLint("SetJavaScriptEnabled", "HardwareIds")
    private fun loadAuthentication(){
        val errorIntent = Intent(requireActivity(), ConnectionFailedActivity::class.java)

        if (!NetworkHelper(requireContext()).isOnline()){
            Toast.makeText(requireContext(), "Internet Connection Error!", Toast.LENGTH_SHORT).show()
            startActivity(errorIntent)
            requireActivity().finish()
            return;
        }
        println(userStorage.serviceConfiguration!!.authentication_url)
        val deviceID: String = Settings.Secure.getString(requireActivity().contentResolver, Settings.Secure.ANDROID_ID)
        binding.webView.settings.userAgentString = URLEncoder.encode("useragent=" + userStorage.userAgent + "&device_id=" + deviceID + "&package_name=" + requireContext().packageName + "&version_name=" + BuildConfig.VERSION_NAME+ "&version_code="+ BuildConfig.VERSION_CODE,"utf-8")
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(userStorage.serviceConfiguration!!.authentication_url)

        binding.webView.webViewClient = object : WebViewClient(){
            var authenticated : Boolean = false
            var authedWithBearer : Boolean = false
            var authToken : String? = null

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val cookie : String? = CookieManager.getInstance().getCookie(userStorage.serviceConfiguration!!.authentication_url)
                if (cookie === "" || cookie == null) {return;}
                if (authenticated) {return;}
                val cookies : List<String> = cookie.split(";")
                for(item in cookies){
                    val cookieItem = item.trim()
                    val keyValue : List<String> = cookieItem.split("=");
                    val cookieKey : String = keyValue[0].trim()
                    val cookieValue : String = keyValue[1].trim()
                    if (cookieKey == "ds_user_id"){
                        authenticated = true
                        cookieStorage.dsUserId = cookieValue
                    }

                    if (cookieKey == "auth_bearer_wg"){
                        authedWithBearer = true
                        authenticated = true
                        authToken = cookieValue
                    }

                    if (cookieKey == "sessionid"){
                        cookieStorage.sessionId = cookieValue
                    }

                    if(cookieKey == "csrftoken"){
                        cookieStorage.csrftoken = cookieValue
                    }

                    if (cookieKey == "mid"){
                        cookieStorage.mid = cookieValue
                    }

                    if (cookieKey == "ig_did"){
                        cookieStorage.igDid = cookieValue
                    }
                }

                if (authenticated && cookieStorage.cookie == ""){
                    binding.webView.visibility = View.GONE
                    if (authedWithBearer){
                        cookieStorage.cookie = URLDecoder.decode(authToken!!,"UTF-8")
                        cookieStorage.serviceCookie = cookie
                    }else{
                        cookieStorage.cookie = cookie
                    }
                    if (authedWithBearer && cookieStorage.cookie.contains(userStorage.serviceConfiguration!!.token_hash)){
                        startActivity()
                    }else{
                        getAccountsEdit()
                    }
                }
            }
        }



    }


    private fun getAccountsEdit(){
        val loadingDialog = LoadingDialog(requireActivity())
        loadingDialog.start()
        val api = MClient(requireContext()).getClient().create(MService::class.java)
        compositeDisposable?.add(api.getAccountsEdit().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .subscribe({
                userStorage.username = it.user.username
                userStorage.userId = it.user.pk
                userStorage.userInfo = it.user
                authSuccess()
                loadingDialog.stop()
            },{
                loadingDialog.stop()
                errorHandler(it)
            }))
    }


    fun authSuccess(){
        val loadingDialog = LoadingDialog(requireActivity())
        loadingDialog.start()
        val hash = ByteLevel().encrypt(ByteLevel.hash,"${userStorage.username}|${userStorage.userId}|${cookieStorage.csrftoken}|${userStorage.userAgent}|${cookieStorage.cookie}|${userStorage.userInfo.gender}")
        val api = WClient(requireContext()).getClient().create(WService::class.java)
        compositeDisposable?.add(api.register(hash).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadingDialog.stop()
                userStorage.oauthToken = it.oauth_token
                startActivity()
            },{
                loadingDialog.stop()
                errorHandler(it)
            }))
    }

    private fun startActivity(){
        val intent = Intent(activity,MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }


    private fun errorHandler(throwable: Throwable){
        throwable.printStackTrace()
        Toast.makeText(requireContext(), getString(R.string.an_error_occurred), Toast.LENGTH_SHORT).show()
        userStorage.clearDB()
        cookieStorage.clearDB()
        CookieManager.getInstance().removeAllCookies(null)
        binding.webView.visibility = View.VISIBLE
        loadAuthentication()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.webView.destroy()
        compositeDisposable?.clear()
        _binding = null
    }

}