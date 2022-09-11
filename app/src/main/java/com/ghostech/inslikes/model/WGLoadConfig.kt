package com.ghostech.inslikes.model

data class WGLoadConfig(
    val privacy_url : String,
    val service_address : String,
    val authentication_url : String,
    val follow_endpoint : String,
    val like_endpoint : String,
    val login_endpoint : String,
    val force_auth : Boolean,
    val token_hash : String
)