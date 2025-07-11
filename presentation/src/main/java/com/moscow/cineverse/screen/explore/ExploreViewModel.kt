package com.moscow.cineverse.screen.explore

import androidx.lifecycle.viewModelScope
import com.android.domain.model.Suggestion
import com.android.domain.usecase.SuggestionUseCase
import com.moscow.cineverse.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ExploreViewModel(
    private val suggestionUseCase: SuggestionUseCase
) :
    BaseViewModel<ExploreScreenState, ExploreScreenEvents>(ExploreScreenState()),
    ExploreInteractionListener {

    private val keywordFlow = MutableStateFlow(uiState.value.searchKeyWord)

    init {
        observeKeyword()
    }

    fun onKeywordChanged(newKeyword: String) {
        keywordFlow.value = newKeyword
    }

    private fun observeKeyword() {
        keywordFlow
            .debounce(300)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { keyword ->
                getSuggestions(keyword)
            }
            .launchIn(viewModelScope)
    }

    private fun getSuggestions(keyword: String, page: Int = 1) {
        launchWithResult(
            action    = { suggestionUseCase.getSuggestions(keyword, page).first() },
            onSuccess = ::onSuccessLoadingSuggestions,
            onError   = { },
            onStart   = { },
            onFinally = { }
        )
    }
    private fun onSuccessLoadingSuggestions(suggestion: List<Suggestion>){

        updateState {
            it.copy(
                suggestions = suggestion
            )
        }
    }
}