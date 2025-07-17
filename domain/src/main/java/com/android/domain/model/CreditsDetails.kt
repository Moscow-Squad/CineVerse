package com.android.domain.model

data class CreditsDetails(
    val actors:List<CastDetails>,
    val behindTheScene:List<CrewDetails>
)

data class CastDetails (
    val id:Int,
    val originalName:String,
    val characterName:String,
    val profileImg:String,
)

data class CrewDetails(
    val id:Int,
    val name:String,
    val job:String,
    val profileImage:String
)