package com.ghostech.inslikes.model.response

import com.ghostech.inslikes.model.User

data class FollowListResponse(
    val users : List<User>,
    val next_max_id : String?,
    val status : String
)
