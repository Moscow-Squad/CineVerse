package com.moscow.cineverse.screen.history

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.mapper.toMediaItemUi
import com.moscow.cineverse.mapper.toUi
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.model.UserType
import com.moscow.domain.usecase.collection.GetCollectionDetailsUseCase
import com.moscow.domain.usecase.local.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getCollectionDetailsUseCase: GetCollectionDetailsUseCase
): BaseViewModel<HistoryScreenState, HistoryEffect>(HistoryScreenState()),
HistoryInteractionListener {
    private val _pagingDataFlow = MutableStateFlow<Flow<PagingData<MediaItemUiState>>>(emptyFlow())
    val pagingDataFlow = _pagingDataFlow.asStateFlow()

    init {
        getMoviesHistory()
    }

    private fun getMoviesHistory() {
        try {
            updateState { it.copy(isLoading = true) }
            val pageSize = 20

            viewModelScope.launch {
                val user = getUserDetailsUseCase()
                val pagingFlow = when (user) {
                    is UserType.AuthenticatedUser -> {
                        createPagingFlow(
                            pageSize = 20,
                            fetchData = { page -> getCollectionDetailsUseCase(user.recentlyCollectionId, page) }
                        )
                    }
                    is UserType.GuestUser -> emptyFlow()
                }
                _pagingDataFlow.value = pagingFlow
                updateState { it.copy(isLoading = false) }
            }
        } catch (e: Exception) {
            updateState { it.copy(isLoading = false, shouldShowError = true) }
        }
    }

    override fun onBackPressed() {

    }

    override fun onRefresh() {
        getMoviesHistory()
    }

    override fun onMediaItemClicked() {
        TODO("Not yet implemented")
    }

    private fun <T : Any> createPagingFlow(
        pageSize: Int,
        fetchData: suspend (Int) -> List<T>
    ): Flow<PagingData<MediaItemUiState>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BasePagingSource(fetchData) }
        ).flow
            .map { pagingData ->
                pagingData.map { item ->
                    when (item) {
                        is Movie -> item.toMediaItemUi()
                        is Series -> item.toUi()
                        else -> throw IllegalArgumentException("Unsupported type: ${item::class.java}")
                    }
                }
            }
            .cachedIn(viewModelScope)
    }
}