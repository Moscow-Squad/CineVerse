package com.moscow.cineverse.screen.home

sealed class SeeMoreHomeEvent {
    data class MovieClicked(val movieId: Int) : SeeMoreHomeEvent()
    data class SeriesClicked(val seriesId: Int) : SeeMoreHomeEvent()
    data class ActorClicked(val actorId: Int) : SeeMoreHomeEvent()
    object NavigateBack : SeeMoreHomeEvent()
}
