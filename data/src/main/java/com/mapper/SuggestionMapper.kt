package com.mapper

import com.remote.dto.SuggestionDto


fun SuggestionDto.toModel(): String {
    return this.name ?: ""
}