package com.moscow.cineverse.screen.model

import com.android.domain.model.Collection

data class CollectionUi(
    val id: Int,
    val name: String,
    val isLoading: Boolean = false
)

fun Collection.toUi() =
    CollectionUi(
        id = id,
        name = name,
        isLoading = false
    )