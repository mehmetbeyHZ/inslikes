package com.ghostech.inslikes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ghostech.inslikes.R
import com.ghostech.inslikes.databinding.FragmentHomeBinding
import com.ghostech.inslikes.helpers.NetworkHelper

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)
        binding.goPrivacy.setOnClickListener {
            if(NetworkHelper(requireContext()).isOnline()){
                navController.navigate(R.id.action_homeFragment_to_privacyFragment)
            }else{
                Toast.makeText(requireContext(), "Lütfen internet bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.authorize.setOnClickListener {
            if(NetworkHelper(requireContext()).isOnline()){
                navController.navigate(R.id.action_homeFragment_to_loginFragment)
            }else{
                Toast.makeText(requireContext(), "Lütfen internet bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}