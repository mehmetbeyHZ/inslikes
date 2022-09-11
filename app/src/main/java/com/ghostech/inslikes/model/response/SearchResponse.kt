package com.ghostech.inslikes.model.response

import com.ghostech.inslikes.model.SearchUser

data class SearchResponse(
    var status : String,
    var users : List<SearchUser>
)
