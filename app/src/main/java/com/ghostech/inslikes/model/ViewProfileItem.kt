package com.ghostech.inslikes.model

data class ViewProfileItem(
    var type : String,
    var images : List<String>,
    var user : User,
    var caption_text : String,
    var like_count : Int,
    var comment_count : Int
)

data class Caption(
    val text : String
);