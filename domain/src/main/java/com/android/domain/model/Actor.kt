package com.android.domain.model

data class Actor(
    val id: Int,
    val name: String,
    val gender: Gender,
    val profileImg: String,
)

enum class Gender(val value: Int){
    MALE(0),
    FEMALE(1)
}