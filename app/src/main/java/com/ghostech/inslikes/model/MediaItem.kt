package com.ghostech.inslikes.model

data class MediaItem(
    var id : String,
    var media_type : Int,
    var code : String,
    var image_versions2 : ImageVersions2,
    var video_versions : List<VideoVersions>,
    var carousel_media_count : Int,
    var carousel_media : List<CarouselItem>,
    var user : User,
    var like_and_view_counts_disabled : Boolean,
    var comment_count : Int,
    var like_count : Int,
    var caption: Caption?
)
