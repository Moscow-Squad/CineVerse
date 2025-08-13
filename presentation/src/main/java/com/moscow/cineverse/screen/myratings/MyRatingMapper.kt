package com.moscow.cineverse.screen.myratings

import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState
import com.moscow.cineverse.screen.explore.toUi
import com.moscow.domain.usecase.rating.GetRatedMoviesUseCase.RatedMovieResult
import com.moscow.domain.usecase.rating.GetRatedSeriesUseCase.RatedSeriesResult

fun RatedSeriesResult.toUi(genres : List<GenreUiState>): RatedMediaItem {
    return RatedMediaItem(
        mediaItem = this.series.toUi(genres),
        rating = this.rating.toInt()
    )
}

fun RatedMovieResult.toUi(genres : List<GenreUiState>): RatedMediaItem {
    return RatedMediaItem(
        mediaItem = this.movie.toUi(genres),
        rating = this.rating.toInt()
    )
}