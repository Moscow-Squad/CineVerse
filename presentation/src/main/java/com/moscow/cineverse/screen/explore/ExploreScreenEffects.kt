package com.moscow.cineverse.screen.explore

import com.moscow.domain.model.Genre
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cineverse.screen.explore.ExploreTabsPages

sealed class ExploreScreenEffects {
    data class GenreSelected(val genre: Genre) : ExploreScreenEffects()
    data class ViewModeChanged(val viewMode: ViewMode) : ExploreScreenEffects()
    data class MovieClicked(val movieId: Int) : ExploreScreenEffects()
    data class SeriesClicked(val seriesId: Int) : ExploreScreenEffects()
    data class TabSelected(val tab: ExploreTabsPages) : ExploreScreenEffects()
    object RefreshRequested : ExploreScreenEffects()
    object LoadData : ExploreScreenEffects()
    data class ActorClicked(val actorId: Int) : ExploreScreenEffects()
}