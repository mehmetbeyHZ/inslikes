package com.ghostech.inslikes.ui.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ghostech.inslikes.databinding.FragmentLikeBinding

class LikeFragment : Fragment() {

    private var _binding : FragmentLikeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikeBinding.inflate(inflater,container,false)
        loadService()
        return _binding!!.root
    }

    private fun loadService(){
        binding.likeWebView.loadUrl("https://begenici.com/login")
    }


}