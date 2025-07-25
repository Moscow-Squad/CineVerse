package com.moscow.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "actor_table",
    foreignKeys = [
        ForeignKey(
            entity = SearchHistoryEntity::class,
            parentColumns = ["searchTerm"],
            childColumns = ["searchTerm"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class ActorEntity(
    @PrimaryKey
    val id: Int = 0,
    val searchTerm: String,
    val name: String,
    val gender: Gender,
    val profileImg: String,
)

enum class Gender {
    MALE,
    FEMALE
}
