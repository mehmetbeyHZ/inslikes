package com.ghostech.inslikes.model

data class StoryItem(
    var taken_at : String,
    var pk : String,
    var media_type : Int,
    var user : User,
    var image_versions2 : ImageVersions2,
    var video_versions : List<VideoVersions>
)
