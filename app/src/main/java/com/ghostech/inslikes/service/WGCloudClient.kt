package com.ghostech.inslikes.service


import android.annotation.SuppressLint
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WGCloudClient () {

    private val baseUrl = "https://webgramb.pro/"

    @SuppressLint("HardwareIds")
    fun getClient() : Retrofit {

        val httpClient : OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.followRedirects(false)

        val gson = GsonBuilder().setLenient().create()
        httpClient.interceptors().add(Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
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