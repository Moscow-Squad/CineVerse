package com.model

data class Actor(
    val id: Int,
    val name: String,
    val gender: Gender,
    val profileImg: String,
)

enum class Gender{
    MALE,
    FEMALE
}