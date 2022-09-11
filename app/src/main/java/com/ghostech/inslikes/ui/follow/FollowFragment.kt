package com.ghostech.inslikes.ui.follow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.ghostech.inslikes.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater,container,false)
        loadService()
        return _binding!!.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadService() {
        binding.followWebView.settings.javaScriptEnabled = true
        binding.followWebView.loadUrl("https://begenici.com")
        binding.followWebView.webViewClient = object : WebViewClient() {
        }
    }

}