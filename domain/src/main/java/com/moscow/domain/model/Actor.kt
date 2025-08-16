package com.moscow.domain.model

import kotlinx.datetime.LocalDate

data class Actor(
    val id: Int,
    val name: String,
    val gender: Gender,
    val birthDate: LocalDate?,
    val placeOfBirth: String,
    val biography: String,
    val profileImg: String,
    val socialMediaLinks: SocialMediaLinks
) {
    data class SocialMediaLinks(
        val youtube: String?,
        val facebook: String?,
        val instagram: String?,
        val tiktok:String?,
        val twitter: String?
    )

    enum class Gender { FEMALE, MALE }
}