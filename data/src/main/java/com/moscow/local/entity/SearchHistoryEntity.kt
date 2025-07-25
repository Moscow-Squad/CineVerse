package com.moscow.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history_table")
data class SearchHistoryEntity(
    @PrimaryKey val searchTerm: String,
)
