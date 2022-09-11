package com.ghostech.inslikes.model.response

import com.ghostech.inslikes.model.MediaItem

data class UserFeedResponse(
    var items : List<MediaItem>,
    var num_results : Int,
    var more_available : Boolean,
    var next_max_id : String?,
    var auto_load_more_enabled : Boolean,
    var status : String
)
