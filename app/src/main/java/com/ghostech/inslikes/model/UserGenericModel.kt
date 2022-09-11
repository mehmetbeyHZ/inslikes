package com.ghostech.inslikes.model

import com.ghostech.inslikes.model.response.UserInfoByIdResponse

data class UserGenericModel(
    val userInfo: UserInfoByIdResponse,
    val medias : List<MediaItem>
)
