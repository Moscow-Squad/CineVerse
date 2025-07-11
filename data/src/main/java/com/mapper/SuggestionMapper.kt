package com.mapper

import com.dto.Result
import com.dto.SuggestionResponse

fun SuggestionResponse.toModel(): List<String> {
    return results?.mapNotNull { it.toModel() } ?: emptyList()
}
fun Result.toModel(): String{
    return this.name?:""
}