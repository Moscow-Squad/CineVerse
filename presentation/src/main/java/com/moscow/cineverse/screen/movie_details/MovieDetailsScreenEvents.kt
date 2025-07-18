package com.moscow.cineverse.screen.movie_details

import com.android.domain.model.Genre
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.screen.castDetails.CastDetailsEvent

sealed class MovieDetailsScreenEvents {
    data object NavigateBack : MovieDetailsScreenEvents()
    data class ShowError(val message: String) : MovieDetailsScreenEvents()
    data class NavigateToFullMovieList(val movieID:Int, val movieTitle:String) : MovieDetailsScreenEvents()
    data class NavigateToFullReviews(val movieID: Int) : MovieDetailsScreenEvents()
    data object NavigateToFullActors: MovieDetailsScreenEvents()
    data object NavigateToFullCast : MovieDetailsScreenEvents()

}

