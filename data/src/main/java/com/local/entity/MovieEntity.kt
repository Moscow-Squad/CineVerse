package com.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies_table",
    foreignKeys = [
        ForeignKey(
            entity = SearchHistoryEntity::class,
            parentColumns = ["searchTerm"],
            childColumns = ["searchTerm"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)data class MovieEntity(
    @PrimaryKey val id: Long = 0,
    val searchTerm: String,
    val name: String,
    val rating: Float,
    val genresId: List<Int>,
    val releaseDate: String,
    val poster: String,
    val duration: String,
)