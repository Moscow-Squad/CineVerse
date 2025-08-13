package com.moscow.cineverse.screen.collection_details

import androidx.paging.PagingData
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class CollectionDetailsScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: Int = 0,
    val showTip: Boolean = true,
    val confirmClear: Boolean = false,
    val moviesGenres: List<GenreUiState> = emptyList(),
    val seriesGenres: List<GenreUiState> = emptyList(),
    val movies: Flow<PagingData<MediaItemUiState>> = flowOf(),
    val enableBlur: String = "high"
)