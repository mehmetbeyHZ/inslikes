package com.ghostech.inslikes.model.response

import com.ghostech.inslikes.model.Reel

data class StoriesResponse(
    var reel : Reel?,
    var status : String
)
