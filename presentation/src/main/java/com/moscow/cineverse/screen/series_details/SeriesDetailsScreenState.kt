package com.moscow.cineverse.screen.series_details

import com.moscow.cineverse.common_ui_state.CrewUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.common_ui_state.ReviewUiState
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cineverse.common_ui_state.StarCastUiState

data class SeriesDetailsScreenState(
    val isLoading: Boolean = false,
    val seriesDetail: SeriesDetailsUiState = SeriesDetailsUiState(),
    val reviews: List<ReviewUiState> = emptyList(),
    val cast: List<StarCastUiState> = emptyList(),
    val crew: List<CrewUiState> = emptyList(),
    val recommendation: List<MediaItemUiState> = emptyList(),
    val shouldShowError: Boolean = false,
    val errorMessage: String = "",
    val viewMode: ViewMode = ViewMode.GRID,
    val showRatingBottomSheet: Boolean = false,
    val starsRating: Int = 0
)

data class SeriesDetailsUiState(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val rating: String = "0.0",
    val genre: String = "",
    val trailerPath: String = "",
    val duration: String = "",
    val releaseDate: String = "",
    val type: String = "SERIES",
    val posterPath: String = "",
    val numberOfSeasons: Int = 0,
    val numberOfEpisodes: Int = 0,
    val seasons: List<SeasonUiState> = emptyList(),
    val creators: List<CrewUiState> = emptyList()
)

data class SeasonUiState(
    val id: Int = 0,
    val title: String = "",
    val airDate: String = "",
    val episodeCount: Int = 0,
    val posterPath: String = "",
    val overview: String = "",
    val rate: String,
)