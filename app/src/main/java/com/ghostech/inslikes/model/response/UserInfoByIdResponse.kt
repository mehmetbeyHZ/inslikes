package com.ghostech.inslikes.model.response

import com.ghostech.inslikes.model.User

data class UserInfoByIdResponse(
    var user : User,
    var status : String
)
