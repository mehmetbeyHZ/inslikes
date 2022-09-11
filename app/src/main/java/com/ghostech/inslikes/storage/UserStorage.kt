package com.ghostech.inslikes.storage

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.google.gson.Gson
import com.ghostech.inslikes.model.User
import com.ghostech.inslikes.model.WGLoadConfig
import java.util.*

class UserStorage (var context : Context) {

    private var sharedPreferences : SharedPreferences = context.getSharedPreferences("com.igplus.followers", Context.MODE_PRIVATE)

    var serviceConfiguration : WGLoadConfig?
        get() { val info = sharedPreferences.getString("service_configuration",null); return Gson().fromJson(info,WGLoadConfig::class.java) }
        set(value) { sharedPreferences.edit().putString("service_configuration", Gson().toJson(value)).apply() }

    val userAgent : String
        get() = String.format(
            "Instagram %s Android(%s/%s; %sdpi; %sx%s %s; %s; %s; %s; %s; %S)", "212.0.0.38.119",
            Build.VERSION.SDK_INT,
            Build.VERSION.RELEASE,
            context.resources.displayMetrics.densityDpi,
            "1080",
            "2131",
            Build.BRAND,
            Build.MODEL,
            Build.PRODUCT,
            Build.BOARD,
            Locale.getDefault().toString(),
            329675731
        )

    var oauthToken : String
        get() = sharedPreferences.getString("oauth_token","")?:""
        set(value) = sharedPreferences.edit().putString("oauth_token",value).apply()



    var userId : String
        get() = sharedPreferences.getString("user_id","")?:""
        set(value) = sharedPreferences.edit().putString("user_id",value).apply()

    var username : String
        get() = sharedPreferences.getString("username","")?:""
        set(value) = sharedPreferences.edit().putString("username",value).apply()

    var userInfo : User
        get() { val info = sharedPreferences.getString("user_info","{}"); return Gson().fromJson(info, User::class.java) }
        set(value) { sharedPreferences.edit().putString("user_info",Gson().toJson(value)).apply() }

    fun clearDB(){
        sharedPreferences.edit().remove("username").apply()
        sharedPreferences.edit().remove("user_id").apply()
        sharedPreferences.edit().remove("web_useragent").apply()
        sharedPreferences.edit().remove("user_info").apply()
        sharedPreferences.edit().remove("search_stories").apply()
        sharedPreferences.edit().remove("search_user_info").apply()
        sharedPreferences.edit().remove("search_user_feeds").apply()
    }

}