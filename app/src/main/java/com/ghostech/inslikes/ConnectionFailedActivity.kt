package com.ghostech.inslikes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ghostech.inslikes.databinding.ActivityConnectionFailedBinding
import com.ghostech.inslikes.helpers.NetworkHelper

class ConnectionFailedActivity : AppCompatActivity() {

    private var _binding : ActivityConnectionFailedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityConnectionFailedBinding.inflate(layoutInflater)
        val view = _binding!!.root
        setContentView(view)
        binding.tryAgain.setOnClickListener {
            if (!NetworkHelper(this).isOnline()){
                Toast.makeText(this, "Lütfen internet bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this@ConnectionFailedActivity,SplashActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}