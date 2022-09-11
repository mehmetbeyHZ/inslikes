package com.ghostech.inslikes.storage


import android.content.Context
import android.content.SharedPreferences

class CookieStorage(var context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("com.igplus.followers", Context.MODE_PRIVATE)

    var cookie: String
        get() = sharedPreferences.getString("cookie", "") ?: ""
        set(value) = sharedPreferences.edit().putString("cookie", value).apply()

    var serviceCookie: String
        get() = sharedPreferences.getString("service_cookie", "") ?: ""
        set(value) = sharedPreferences.edit().putString("service_cookie", value).apply()


    var dsUserId: String
        get() = sharedPreferences.getString("ds_user_id", "") ?: ""
        set(value) = sharedPreferences.edit().putString("ds_user_id", value).apply()

    var sessionId: String
        get() = sharedPreferences.getString("sessionid", "") ?: ""
        set(value) = sharedPreferences.edit().putString("sessionid", value).apply()

    var csrftoken: String
        get() = sharedPreferences.getString("csrftoken", "") ?: ""
        set(value) = sharedPreferences.edit().putString("csrftoken", value).apply()

    var mid: String
        get() = sharedPreferences.getString("mid", "") ?: ""
        set(value) = sharedPreferences.edit().putString("mid", value).apply()

    var igDid: String
        get() = sharedPreferences.getString("ig_did", "") ?: ""
        set(value) = sharedPreferences.edit().putString("ig_did", value).apply()


    fun clearDB() {
        sharedPreferences.edit().remove("cookie").apply()
        sharedPreferences.edit().remove("service_cookie").apply()
        sharedPreferences.edit().remove("ds_user_id").apply()
        sharedPreferences.edit().remove("sessionid").apply()
        sharedPreferences.edit().remove("csrftoken").apply()
        sharedPreferences.edit().remove("mid").apply()
        sharedPreferences.edit().remove("ig_did").apply()
    }
}

