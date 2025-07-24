package com.moscow.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavouriteGenre")
data class FavouriteGenreEntity (
    @PrimaryKey val genreId: Int,
    val count: Int
)
