package com.moscow.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(tableName = "history_item")
data class HistoryItemEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val posterPath: String,
    val itemType: String,
    val rating: Float,
    val releaseDate: LocalDate,
    val cacheTimestamp: Long = System.currentTimeMillis()
)