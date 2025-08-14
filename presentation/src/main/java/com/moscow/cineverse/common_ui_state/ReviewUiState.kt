package com.moscow.cineverse.common_ui_state

import kotlinx.datetime.LocalDate

data class ReviewUiState(
    val id: String,
    val name: String,
    val username: String,
    val rate: Float,
    val reviewContent: String,
    val date: LocalDate?,
    val userImageUrl: String
)