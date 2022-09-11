package com.ghostech.inslikes.model.response

data class VisitorsResponse(
    val status : String,
    val users : List<VisitorUser>
)

data class VisitorUser(
    val pk : String,
    val username : String,
    val full_name : String,
    val is_private : Boolean,
    val is_verified : Boolean,
    val profile_pic_url : String
)