package com.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavouriteGenre")
data class FavouriteGenreEntity (
    @PrimaryKey val genre: String,
    val count: Int
)