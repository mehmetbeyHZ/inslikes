package com.ghostech.inslikes.model

data class ImageVersions2(
    var candidates : List<Candidates>
)

data class Candidates(
    var width : Int,
    var height : Int,
    var url : String
)
