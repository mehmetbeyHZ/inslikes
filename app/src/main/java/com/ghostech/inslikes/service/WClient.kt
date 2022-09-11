package com.ghostech.inslikes.service

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.google.gson.GsonBuilder
import com.ghostech.inslikes.BuildConfig
import com.ghostech.inslikes.storage.UserStorage
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WClient (val context : Context) {

    private var userStorage = UserStorage(context)
    private val baseUrl = "${userStorage.serviceConfiguration!!.service_address}api/"

    @SuppressLint("HardwareIds")
    public fun getClient() : Retrofit {
        val xWgToken = userStorage.oauthToken

        val deviceID: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

        val httpClient : OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.followRedirects(false)

        val gson = GsonBuilder().setLenient().create()
        httpClient.interceptors().add(Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("x-wg-token",xWgToken)
                .addHeader("app-language","tr")
                .addHeader("x-device-id",deviceID)
                .addHeader("app-version-code", BuildConfig.VERSION_CODE.toString())
                .addHeader("app-version-name",BuildConfig.VERSION_NAME)
            chain.proceed(request.build())
        })
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}