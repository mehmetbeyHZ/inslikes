package com.ghostech.inslikes.model.response

import com.ghostech.inslikes.model.User

data class SearchUserResponse(
    var users : List<User>,
    var status : String
)
