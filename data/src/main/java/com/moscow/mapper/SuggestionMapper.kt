package com.moscow.mapper

import com.moscow.remote.dto.suggestion.SuggestionDto

fun SuggestionDto.toModel(): String {
    return this.name ?: ""
}