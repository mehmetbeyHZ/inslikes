package com.ghostech.inslikes.model.response

data class InformationResponse(
    val status : String,
    val notifications : List<NotificationItem>
)
data class NotificationItem(
    val image : String?,
    val title : String,
    val description : String,
    val route : String?
)
