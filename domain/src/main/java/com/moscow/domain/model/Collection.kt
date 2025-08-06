package com.moscow.domain.model

data class Collection(
    val id: Int,
    val name: String,
    val itemCount: Int,
    val listType: MediaType
)