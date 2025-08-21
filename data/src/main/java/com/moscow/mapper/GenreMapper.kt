package com.moscow.mapper

import com.moscow.domain.model.Genre
import com.moscow.local.entity.GenreEntity
import com.moscow.remote.dto.genre.GenreDto

fun GenreDto.toEntity(): GenreEntity =
    GenreEntity(
        id = id,
        name = name,
        timestamp = System.currentTimeMillis()
    )

fun GenreEntity.toDomain(): Genre =
    Genre(
        id = id,
        name = name
    )