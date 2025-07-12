package com.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "series_table",
    foreignKeys = [
        ForeignKey(
            entity = SearchHistoryEntity::class,
            parentColumns = ["searchTerm"],
            childColumns = ["searchTerm"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class SeriesEntity(
    @PrimaryKey
    val id: Int = 0,
    val searchTerm: String,
    val name: String,
    val genresId: List<Int>,
    val description: String,
    val rating: Float,
    val releaseDate: LocalDate,
    val poster: String,
)