package com.ghostech.inslikes.model.response

data class RegisterResponse(
    val token : String,
    val token_id : String,
    val oauth_token : String
)
