package com.moscow.cineverse.screen.explore

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState.MediaType
import com.moscow.cineverse.screen.explore.ExploreScreenState.ActorUiState
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState
import com.moscow.domain.model.Actor
import com.moscow.domain.model.Genre
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series

const val YYYY_MMM_DD = "yyyy, MMM dd"

fun Movie.toUi(genresList: List<GenreUiState> = listOf()): MediaItemUiState =
    MediaItemUiState(
        id = id,
        title = title,
        posterPath = posterUrl,
        rating = rating,
        genres = if (genresList.isEmpty()) emptyList() else
            genreIds.mapNotNull { id ->
            genresList.firstOrNull { genre -> genre.id == id }?.name
        },
        releaseDate = releaseDate,
        mediaType = MediaType.Movie,
        backdropPath = backdropUrl
    )

fun Series.toUi(genresList: List<GenreUiState> = listOf()): MediaItemUiState =
    MediaItemUiState(
        id = id,
        title = title,
        posterPath = posterPath,
        rating = rating,
        genres = if (genresList.isEmpty()) emptyList() else
            genreIds.mapNotNull { id ->
                genresList.firstOrNull { genre -> genre.id == id }?.name
            },
        releaseDate = releaseDate,
        mediaType = MediaType.Series,
        backdropPath = this.backdropPath
    )

fun Actor.toUi(): ActorUiState =
    ActorUiState(
        title = name,
        profilePath = profileImg,
        id = id
    )

fun Genre.toUi() =
    GenreUiState(
        id = id,
        name = name
    )

fun ExploreTabsPages.toTitle(): String {
    return when (this) {
        ExploreTabsPages.MOVIES -> "Movies"
        ExploreTabsPages.SERIES -> "Series"
        ExploreTabsPages.ACTORS -> "Actors"
    }
}
