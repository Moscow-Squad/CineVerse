package com.moscow.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(tableName = "home_item")
data class HomeItemEntity(
    @PrimaryKey
    val id: Int,
    val categoryType: String,
    val name: String,
    val posterPath: String,
    val backdropPath: String,
    val itemType: String,
    val rating: Float,
    val genreIds: List<Int>,
    val releaseDate: LocalDate,
    val cacheTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "home_category_timestamp")
data class HomeCategoryTimestampEntity(
    @PrimaryKey
    val categoryType: String,
    val lastRefreshed: Long = System.currentTimeMillis()
)