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
){
    data class SocialMediaLinks(
        val youtube: String? = null,
        val facebook: String? = null,
        val instagram: String? = null
    )

    enum class Gender(val value: Int){
        FEMALE(1),
        MALE(2)
    }

}