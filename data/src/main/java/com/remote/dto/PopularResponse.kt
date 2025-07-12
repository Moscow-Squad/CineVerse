package com.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PopularResponse<T>(
    val results: List<T>
)