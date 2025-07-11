package com.moscow.cineverse.screen.explore

import com.android.domain.model.Suggestion

data class ExploreScreenState(
    val searchKeyWord: String = "",
    val suggestions:List<Suggestion> = emptyList()
)
