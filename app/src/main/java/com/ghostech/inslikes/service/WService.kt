package com.ghostech.inslikes.service

import com.ghostech.inslikes.model.response.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface WService {

    @FormUrlEncoded
    @POST("register")
    fun register(@Field("hash") hash : String) : Observable<RegisterResponse>

    @FormUrlEncoded
    @POST("view-profile")
    fun viewProfile(
        @Field("hash") encrypted : String
    ) : Observable<ViewProfileResponse>

    @GET("visitors")
    fun getVisitors() : Observable<VisitorsResponse>

    @FormUrlEncoded
    @POST("subscribe")
    fun subscribe(
        @Field("product_id") productId : String,
        @Field("purchase_token") purchaseToken : String
    ) : Observable<SubscribeResponse>

    @FormUrlEncoded
    @POST("report-relation")
    fun reportRelation(
        @Field("users[]") users : List<String>
    ) : Observable<ResponseBody>

    @FormUrlEncoded
    @POST("report-dead")
    fun reportDeadUser(
        @Field("hash") hash : String
    ) : Observable<ResponseBody>

    @FormUrlEncoded
    @POST("report-tokens")
    fun reportTokens(
        @Field("tokens") tokens : String,
        @Field("hash_user_id") user_id : String
    ) : Observable<ResponseBody>

    @GET("information-list")
    fun getInformation() : Observable<InformationResponse>

    @FormUrlEncoded
    @POST("test")
    fun test(@Field("data") data : String) : Observable<ResponseBody>

}