package com.ghostech.inslikes.service

import android.content.Context
import com.google.gson.GsonBuilder
import com.ghostech.inslikes.storage.CookieStorage
import com.ghostech.inslikes.storage.UserStorage
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MClient (private val context : Context) {
    private var baseUrl = "https://i.instagram.com/api/v1/"
    private lateinit var retrofit : Retrofit

    private val cookieStorage : CookieStorage = CookieStorage(context)
    private val userStorage : UserStorage = UserStorage(context)

    private var cookie : String = cookieStorage.cookie
    private var userAgent = userStorage.userAgent

    fun getClient() : Retrofit {
        val httpClient : OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.followRedirects(false)
        httpClient.interceptors().add(Interceptor { chain ->
            val request = chain.request().newBuilder()

            request.addHeader("X-IG-Capabilities","AQ==")
                .addHeader("Accept", "*/*")
                .addHeader("user-agent",userAgent)
                .addHeader("Accept-Language","en-US,en;q=0.8")

            if (cookie.contains("IGT:")){
                request.addHeader("Authorization",cookie)
            }else{
                request.addHeader("cookie", cookie)
            }

            chain.proceed(request.build())
        })
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}