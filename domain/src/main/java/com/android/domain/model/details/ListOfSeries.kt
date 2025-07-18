package com.android.domain.model.details

data class ListOfSeries(
    val id: Int,
    val page: Int,
    val results: List<SeriesItem>,
    val totalPages: Int,
    val totalResults: Int,
)

data class SeriesItem(
    val id: Int,
    val name: String,
    val description: String,
    val favoriteCount: Int,
    val itemCount: Int,
    val iso6391: String,
    val iso31661: String,
    val posterPath: String?,
)
