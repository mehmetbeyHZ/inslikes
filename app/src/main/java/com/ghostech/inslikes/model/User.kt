package com.ghostech.inslikes.model

data class User(
    var id: String,
    var biography : String,
    var external_url: String,
    var edge_followed_by : SocialCount,
    var edge_follow: SocialCount,
    var full_name : String,
    var username : String,
    var is_private : Boolean,
    var is_verified : Boolean,
    var profile_pic_url : String,
    var profile_pic_url_hd : String,

    var friendship_status : FriendshipStatus,
    var edge_owner_to_timeline_media : SocialCount,

    // Mobile user

    var pk : String,
    var gender : Int,
    var follower_count : Int,
    var following_count : Int,
    var media_count : Int,

)
