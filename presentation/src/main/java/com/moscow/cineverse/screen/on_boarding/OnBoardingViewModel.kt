package com.moscow.cineverse.screen.on_boarding

import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.utlis.StringValue
import com.moscow.cinverse.presentation.R
import com.moscow.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userRepository: UserRepository
) :
    BaseViewModel<OnBoardingState, OnBoardingScreenEvents>(OnBoardingState()),
    OnBoardingInteractionListener {

    init {
        getPages()
    }

    private fun getPages() {
        updateState {
            it.copy(
                pages =
                    listOf(
                        PageUiState(
                            imageResId = R.drawable.on_boarding_1,
                            title = StringValue.StringResource(R.string.welcome_to_your_movie_universe),
                            description = StringValue.StringResource(R.string.discover_track_and_rate_your_favorite_movies_series)
                        ),
                        PageUiState(
                            imageResId = R.drawable.on_boarding_2,
                            title = StringValue.StringResource(R.string.track_everything),
                            description = StringValue.StringResource(R.string.your_watchlist_your_ratings_all_in_one_place)
                        ),
                        PageUiState(
                            imageResId = R.drawable.on_boarding_3,
                            title = StringValue.StringResource(R.string.personalized_recommendations),
                            description = StringValue.StringResource(R.string.answer_fun_questions_to_get_handpicked_recommendations)
                        )
                    )
            )
        }
    }

    override fun onPageChanged(pageIndex: Int) {
        updateState { it.copy(currentPage = pageIndex) }
    }

    override fun onClickPreviousButton() {
        val prev = uiState.value.currentPage - 1
        if (prev >= 0) {
            updateState { it.copy(currentPage = prev) }
        }
    }

    override fun onClickNextButton() {
        val next = uiState.value.currentPage + 1
        if (next < uiState.value.pages.size) {
            updateState { it.copy(currentPage = next) }
        }
    }

    override fun onClickGetStartedButton() {
        viewModelScope.launch {
            userRepository.setOnBoardingCompleted()
            sendEvent(OnBoardingScreenEvents.NavigateToLoginScreen)
        }
    }
}