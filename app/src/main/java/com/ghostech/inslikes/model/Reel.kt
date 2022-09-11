package com.ghostech.inslikes.model

data class Reel(
    val id : String,
    val user : User,
    val items : List<StoryItem>
)
