package com.moscow.cineverse.presentation.screens.search

import androidx.lifecycle.viewModelScope
import com.android.domain.usecase.GetLocalSuggestions
import com.moscow.cineverse.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(
    val getLocalSuggestions: GetLocalSuggestions,
) : BaseViewModel<SearchUiState, SearchEffect>(SearchUiState()) {

    fun onSearchBarClickedOn(){
        updateState { it.copy(isSearchBarClickedOn = true) }
    }

    fun getHistoryData(){
        launchWithResult<Flow<List<String>>>(
            action = {getLocalSuggestions()},
            onSuccess = { flow ->
                viewModelScope.launch{
                    flow.collect { history ->
                        val suggestions = history.map { SuggestItemUiState(it, isHistory = true) }
                        updateState { it.copy(history = suggestions) }
                    }
                }
            },
            onError = {},
            onStart = { updateState { it.copy(isLoading = true) } },
            onFinally = { updateState { it.copy(isLoading = false, showHistory = true) } }
        )
    }
}