package com.moscow.cineverse.screen.collection_details

data class CollectionDetailsScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: String = "",
)
