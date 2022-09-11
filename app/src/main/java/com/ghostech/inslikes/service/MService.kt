package com.ghostech.inslikes.service

import com.ghostech.inslikes.model.response.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface MService {

    @GET("users/{username}/usernameinfo/?from_module=feed_timeline")
    fun getUserInfo(@Path("username") username : String) : Observable<UserInfoByIdResponse>

    @GET("accounts/current_user/?edit=true")
    fun getAccountsEdit() : Observable<UserInfoByIdResponse>

    @GET("graphql/query/")
    fun getMedias(@Query("query_id") query_id : String, @Query("id") id : String, @Query("first") first : String) : Observable<UserMediasResponse>

    @GET("users/search/?search_surface=user_search_page&timezone_offset=0&count=30")
    fun search(@Query("q") query : String) : Observable<SearchUserResponse>

    // Mobile API

    @GET("feed/user/{userid}/?exclude_comment=true&only_fetch_first_carousel_media=false&count=30")
    fun getUserFeeds(@Path("userid") userid : String, @Query("max_id") max_id : String?) : Observable<UserFeedResponse>

    @GET("highlights/{userid}/highlights_tray/")
    fun getHighlightsTray(@Path("userid") userid : String) : Observable<ResponseBody>

    @GET("feed/user/{userid}/story/")
    fun getStory(@Path("userid") userid : String) : Observable<StoriesResponse>

    @GET("users/{userid}/info/")
    fun getUserInfoById(@Path("userid") userid : String) : Observable<UserInfoByIdResponse>

    @GET("friendships/{userid}/following/?includes_hashtags=false&search_surface=follow_list_page&query=&enable_groups=true")
    fun getFollowers(@Path("userid") userid : String,@Query("rank_token") rank_token : String,@Query("max_id") max_id : String?) : Observable<FollowListResponse>

}