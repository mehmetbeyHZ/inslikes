package com.ghostech.inslikes.ui.dialogs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ghostech.inslikes.databinding.DialogInformationBinding

class InformationDialog (var title : String, var description : String,var buttonText : String, var route : String) : DialogFragment() {

    private var _binding : DialogInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogInformationBinding.inflate(inflater,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeDialog.setOnClickListener {
            dismiss()
        }
        binding.title.text = title
        binding.description.text = description
        if (route == "NO_ROUTE"){
            binding.button.visibility = View.GONE
        }else{
            binding.button.text = buttonText
            binding.button.setOnClickListener {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(route))
                    startActivity(intent)
                }catch (it : Throwable){

                }
            }
        }
    }
}