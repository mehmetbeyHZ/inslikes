package com.ghostech.inslikes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ghostech.inslikes.helpers.NetworkHelper
import com.ghostech.inslikes.service.WGCloudClient
import com.ghostech.inslikes.service.WGCloudService
import com.ghostech.inslikes.storage.UserStorage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var compositeDisposable: CompositeDisposable? = null
    private lateinit var userStorage: UserStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userStorage = UserStorage(this)
        compositeDisposable = CompositeDisposable()
        loadConfigurations()
    }

    private fun loadConfigurations() {
        val intent = Intent(this, HomeActivity::class.java)
        val errorIntent = Intent(this,ConnectionFailedActivity::class.java)

        if (!NetworkHelper(this).isOnline()){
            startActivity(errorIntent)
            finish()
        }else{

            val api = WGCloudClient().getClient().create(WGCloudService::class.java)
            compositeDisposable?.add(api.getServices("inslike").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userStorage.serviceConfiguration = it
                    startActivity(intent)
                    finish()
                }, {
                    it.printStackTrace()
                    if(userStorage.serviceConfiguration != null){
                        startActivity(intent)
                    }else{
                        startActivity(errorIntent)
                    }

                    finish()

                })
            )
        }
    }

}