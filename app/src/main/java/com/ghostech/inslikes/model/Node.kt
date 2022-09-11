package com.ghostech.inslikes.model

data class Node(
    var id : String,
    var shortcode : String,
    var display_url: String,
    var edge_media_preview_like : SocialCount
)
