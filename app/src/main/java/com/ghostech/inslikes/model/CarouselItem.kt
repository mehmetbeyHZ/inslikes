package com.ghostech.inslikes.model

data class CarouselItem(
    var id : String,
    var media_type : Int,
    var video_versions : List<VideoVersions>,
    var image_versions2 : ImageVersions2
)