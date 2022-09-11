package com.ghostech.inslikes.ui.privacy

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ghostech.inslikes.ConnectionFailedActivity
import com.ghostech.inslikes.databinding.FragmentPrivacyBinding
import com.ghostech.inslikes.helpers.NetworkHelper
import com.ghostech.inslikes.storage.UserStorage
import com.ghostech.inslikes.ui.dialogs.LoadingDialog

class PrivacyFragment : Fragment() {

    private lateinit var userStorage: UserStorage

    private lateinit var binding: FragmentPrivacyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrivacyBinding.inflate(inflater,container,false)
        userStorage = UserStorage(requireContext())
        binding.webView.visibility = View.GONE
        loadWebView()
        return binding.root
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView(){
        val errorIntent = Intent(requireActivity(),ConnectionFailedActivity::class.java)

        if (!NetworkHelper(requireContext()).isOnline()){
            Toast.makeText(requireContext(), "Internet Connection Error!", Toast.LENGTH_SHORT).show()
            startActivity(errorIntent)
            requireActivity().finish()
            return;
        }

        val loadingDialog = LoadingDialog(requireActivity())

        binding.webView.visibility = View.VISIBLE
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(userStorage.serviceConfiguration!!.privacy_url)
        binding.webView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                loadingDialog.start()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                loadingDialog.stop()
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                binding.webView.visibility = View.GONE
                startActivity(errorIntent)
            }
        }
    }


}