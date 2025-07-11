package com.moscow.cineverse.screen.explore

import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages

sealed class ExploreScreenEvents {
    data class GenreSelected(val genre: Genre) : ExploreScreenEvents()
    data class ViewModeChanged(val viewMode: ViewMode) : ExploreScreenEvents()
    data class MovieClicked(val movie: Movie) : ExploreScreenEvents()
    data class TabSelected(val tab: ExploreTabsPages) : ExploreScreenEvents()
    object RefreshRequested : ExploreScreenEvents()
    object LoadData : ExploreScreenEvents()
}