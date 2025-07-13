package com.android.domain.model

import kotlinx.datetime.LocalDate

data class ActorDetails(
    val id: Int,
    val date: LocalDate,
    val location: String,
    val youtubeLink: String,
    val facebookLink: String,
    val instagramLink: String,
    val biography: String
)