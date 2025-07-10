package com.mapper

import com.android.domain.model.Suggestion
import com.dto.Result
import com.dto.SuggestionResponse

fun SuggestionResponse.toModel(): List<Suggestion> {
    return results?.mapNotNull { it.toModel() } ?: emptyList()
}
fun Result.toModel(): Suggestion{
    return Suggestion(
        id = this.id!!,
        name = this.name?:""
    )
}