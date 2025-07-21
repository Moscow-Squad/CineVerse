package com.moscow.cineverse.screen.explore

import com.android.domain.model.Genre
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages

sealed class ExploreScreenEvents {
    data class GenreSelected(val genre: Genre) : ExploreScreenEvents()
    data class ViewModeChanged(val viewMode: ViewMode) : ExploreScreenEvents()
    data class MovieClicked(val movieId: Int) : ExploreScreenEvents()
    data class SeriesClicked(val seriesId: Int) : ExploreScreenEvents()
    data class TabSelected(val tab: ExploreTabsPages) : ExploreScreenEvents()
    object RefreshRequested : ExploreScreenEvents()
    object LoadData : ExploreScreenEvents()
    data class ActorClicked(val actorId: Int) : ExploreScreenEvents()
}