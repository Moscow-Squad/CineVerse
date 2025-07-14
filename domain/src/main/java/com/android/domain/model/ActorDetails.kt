package com.android.domain.model

import kotlinx.datetime.LocalDate

data class ActorDetails(
    val id: Int,
    val birthDate: LocalDate,
    val placeOfBirth: String,
    val youtubeLink: String,
    val facebookLink: String,
    val instagramLink: String,
    val biography: String,
    val profileImg: String
)