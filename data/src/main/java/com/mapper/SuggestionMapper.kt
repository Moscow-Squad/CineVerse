package com.mapper

import com.remote.dto.suggestion.SuggestionDto


fun SuggestionDto.toModel(): String {
    return this.name ?: ""
}