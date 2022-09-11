package com.ghostech.inslikes.model.response

import com.ghostech.inslikes.model.ServerSubscriptionState

data class ViewProfileResponse(
    var p0 : String,
    var p1 : String,
    var p2 : String,
    var p3 : ServerSubscriptionState?,
    var status : String
)
