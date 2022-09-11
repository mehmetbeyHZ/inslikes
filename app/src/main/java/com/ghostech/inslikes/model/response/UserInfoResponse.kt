package com.ghostech.inslikes.model.response

import com.ghostech.inslikes.model.Graphql


data class UserInfoResponse(
    var logging_page_id : String,
    var graphql : Graphql
)