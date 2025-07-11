package com.moscow.cineverse.screen.explore

interface ExploreInteractionListener {
    fun searchMovie()
    fun searchSeries()
    fun searchActor()
    fun getSavedHistoryItems(suggestion: String)
}