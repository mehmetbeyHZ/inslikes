package com.ghostech.inslikes.model

data class FriendshipStatus(
    var following : Boolean,
    var is_private : Boolean,
    var incoming_request : Boolean,
    var outgoing_request : Boolean,
    var is_bestie : Boolean,
    var is_restricted : Boolean
)