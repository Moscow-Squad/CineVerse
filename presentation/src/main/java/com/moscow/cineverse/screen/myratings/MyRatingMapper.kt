package com.moscow.cineverse.screen.myratings

import com.moscow.cineverse.screen.explore.toUi
import com.moscow.domain.usecase.movie.GetRatedMoviesUseCase.RatedMovieResult
import com.moscow.domain.usecase.series.GetRatedSeriesUseCase.RatedSeriesResult

fun RatedSeriesResult.toUi(): RatedMediaItem {
    return RatedMediaItem(
        mediaItem = this.series.toUi(listOf()),
        rating = this.rating.toInt()
    )
}

fun RatedMovieResult.toUi(): RatedMediaItem {
    return RatedMediaItem(
        mediaItem = this.movie.toUi(listOf()),
        rating = this.rating.toInt()
    )
}