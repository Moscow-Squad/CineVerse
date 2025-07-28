package com.moscow.cineverse.screen.see_more

sealed class SeeMoreEvent {
    data class MovieClicked(val movieId: Int) : SeeMoreEvent()
    data class SeriesClicked(val seriesId: Int) : SeeMoreEvent()
    data class ActorClicked(val actorId: Int) : SeeMoreEvent()
    object NavigateBack : SeeMoreEvent()
}
