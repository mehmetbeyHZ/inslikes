package com.ghostech.inslikes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.ghostech.inslikes.databinding.ActivityMainBinding
import com.ghostech.inslikes.helpers.NetworkHelper
import com.ghostech.inslikes.storage.CookieStorage
import com.ghostech.inslikes.storage.UserStorage
import com.ghostech.inslikes.ui.dialogs.LoadingDialog
import com.onesignal.OneSignal
import android.webkit.JavascriptInterface
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions
import com.ghostech.inslikes.ui.dialogs.InformationDialog


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var userStorage : UserStorage
    private lateinit var cookieStorage: CookieStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        userStorage = UserStorage(this)
        cookieStorage = CookieStorage(this)
        setContentView(view)
        MobileAds.initialize(this) {}

        binding.adView.loadAd(AdRequest.Builder().build())


        registerOneSignal()
        configureWebView()
        rewardedAds()

        if(cookieStorage.cookie.contains(userStorage.serviceConfiguration!!.token_hash)){
            binding.freeCoinAds.visibility = View.GONE
        }

        binding.freeCoinAds.setOnClickListener {
            if (mRewardedAd != null){
                mRewardedAd?.show(this, object : OnUserEarnedRewardListener {
                    override fun onUserEarnedReward(rewardItem: RewardItem) {
                        binding.freeCoinAds.visibility = View.GONE
                    }
                })
            }else{
                Toast.makeText(this, "Şuanda görüntülenecek reklam yok, daha sonra tekrar deneyin.", Toast.LENGTH_SHORT).show()
            }
        }



        binding.bottomNavigationView.setOnItemSelectedListener {

            val itemID = it.itemId

            if (itemID == R.id.followFragment){
                followActions()
            }else if (itemID == R.id.likeFragment){
                likeActions()
            }else if(itemID == R.id.refreshFragment){
                binding.webView.loadUrl(binding.webView.url.toString())
                return@setOnItemSelectedListener false
            }else if(itemID == R.id.logoutTheApp){
                logout()
            }

            return@setOnItemSelectedListener true
        }

    }

    inner class WebViewInterface(val context: Context){
        @JavascriptInterface
        fun openDialogMessage(title: String,description:String, buttonText : String, route : String) {
            val information = InformationDialog(title,description,buttonText,route)
            information.show(supportFragmentManager,"DIALOG_SERVICE")
        }

    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun configureWebView(){
        val config = userStorage.serviceConfiguration!!;
        binding.webView.settings.javaScriptEnabled = true

        binding.webView.addJavascriptInterface(WebViewInterface(this),"Android")

        val firstRouting = config.service_address+config.follow_endpoint


        val cookies = cookieStorage.serviceCookie
        val cookiesList = cookies.split(";")
        cookiesList.forEach { item ->
            CookieManager.getInstance().setCookie(userStorage.serviceConfiguration!!.service_address, item)
        }

        val loadingDialog = LoadingDialog(this)

        binding.webView.settings.userAgentString = "InsLike V4.0 (Android Gecko)"
        binding.webView.loadUrl(firstRouting)
        binding.webView.webViewClient = object : WebViewClient(){

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                loadingDialog.start()
            }



            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                if (!NetworkHelper(this@MainActivity).isOnline()){
                    binding.webView.visibility = View.GONE
                    startConnectionFailActivity()
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                loadingDialog.stop()
                super.onPageFinished(view, url)
                url?.let {
                    val followEndpoint = config.service_address+config.follow_endpoint
                    val likeEndpoint = config.service_address+config.like_endpoint
                    if (it.contains("app/follow") || it.contains("app/likes") || it.contains("app/g-likes") || it.contains("app/g-follow")){

//                        if(binding.bottomNavigationView.selectedItemId == R.id.followFragment && it != followEndpoint){
//                            followActions()
//                        }
//                        if(binding.bottomNavigationView.selectedItemId == R.id.likeFragment && it != likeEndpoint){
//                            likeActions()
//                        }

                    }else{
                        logout()
                    }
                }
            }

            override fun onReceivedError(
                view: WebView?, request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                //binding.webView.visibility = View.GONE
            }
        };
    }

    private fun followActions(){
        val config = userStorage.serviceConfiguration!!
        binding.webView.loadUrl(config.service_address+config.follow_endpoint)
    }

    private fun likeActions(){
        val config = userStorage.serviceConfiguration!!
        binding.webView.loadUrl(config.service_address+config.like_endpoint)
    }

    private fun startConnectionFailActivity(){
        val intent = Intent(this,ConnectionFailedActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerOneSignal(){
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(BuildConfig.ONE_HASH_KEY)
    }

    private fun logout(){
        userStorage.clearDB()
        cookieStorage.clearDB()
        val finishStorage = Intent(this@MainActivity,HomeActivity::class.java)
        startActivity(finishStorage)
        finish()
    }

    private var mRewardedAd: RewardedAd? = null

    private fun rewardedAds(){
        val adRequest = AdRequest.Builder().build()

        val ssv = ServerSideVerificationOptions.Builder()
            .setCustomData(userStorage.userId)
            .build()

        RewardedAd.load(this,"ca-app-pub-4271775917374553/5076126610", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                println(adError.message)
                binding.freeCoinAds.visibility = View.GONE
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {

                if(!cookieStorage.cookie.contains(userStorage.serviceConfiguration!!.token_hash)){
                    binding.freeCoinAds.visibility = View.VISIBLE
                }
                mRewardedAd = rewardedAd
                mRewardedAd?.setServerSideVerificationOptions(ssv)
            }
        })
    }

}