package com.ghostech.inslikes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ghostech.inslikes.storage.CookieStorage
import com.onesignal.OneSignal

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAuth()
        setContentView(R.layout.activity_home)
    }

    private fun checkAuth() {
        val cookieStorage = CookieStorage(this)
        if (cookieStorage.cookie != "") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
            OneSignal.initWithContext(this)
            OneSignal.setAppId(BuildConfig.ONE_HASH_KEY)
        }
    }

}