package com.ghostech.inslikes.service

import com.ghostech.inslikes.model.WGLoadConfig
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WGCloudService {

    @GET("services")
    fun getServices(@Query("service") service : String) : Observable<WGLoadConfig>

}