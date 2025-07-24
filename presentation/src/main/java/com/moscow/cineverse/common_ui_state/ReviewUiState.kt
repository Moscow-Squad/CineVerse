package com.moscow.cineverse.common_ui_state

data class ReviewUiState(
    val id: String,
    val name:String,
    val username:String,
    val rate:Int,
    val reviewContent:String,
    val date: String,
    val userImage:String
)