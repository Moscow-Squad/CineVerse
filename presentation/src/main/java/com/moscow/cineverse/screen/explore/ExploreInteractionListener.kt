package com.moscow.cineverse.screen.explore

import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages

interface ExploreInteractionListener {
    fun onGenreSelected(genre: Genre)
    fun onViewModeChanged(viewMode: ViewMode)
    fun onMovieClick(movie: Movie)
    fun onTabSelected(tab: ExploreTabsPages)
    fun onRefresh()
}