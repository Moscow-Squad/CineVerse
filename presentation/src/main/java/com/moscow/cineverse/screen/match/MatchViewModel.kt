package com.moscow.cineverse.screen.match

import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.screen.explore.toUi
import com.moscow.domain.model.Movie
import com.moscow.domain.usecase.genre.GenreUseCase
import com.moscow.domain.usecase.match.GetMatchedMovies
import com.moscow.domain.usecase.movie.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val getMatchedMovies: GetMatchedMovies,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val genreUseCase: GenreUseCase,
) : BaseViewModel<MatchUiState, MatchEvent>(MatchUiState()),
    MatchInteractionListener {

    private fun getGenres() {
        updateState { it.copy(isLoading = true, errorMessage = null) }
        launchWithResult(
            action = { genreUseCase.getMoviesGenres() },
            onSuccess = { genres ->
                updateState {
                    it.copy(movieGenre = genres.map { genre -> genre.toUi() })
                }
                loadMatchData()
            },
            onError = { e ->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = e
                    )
                }
            },
        )
    }

    override fun onClickStartMatching() {
        updateState { it.copy(currentPage = MatchPages.QuestionsPage) }
    }

    override fun onClickFinishMatching() {
        updateState { it.copy(currentPage = MatchPages.ResultsPage) }
    }

    override fun onClickNextQuestion() {
        if (uiState.value.isNextButtonActivated)
            updateState { state ->
                val nextIndex = state.currentQuestionType.ordinal + 1
                QuestionType.entries.getOrNull(nextIndex)
                    ?: run {
                        getGenres()
                        return@updateState state.copy(isLoadingRecommendations = true)
                    }

                state.copy(currentQuestionType = QuestionType.entries[nextIndex])

            }
    }

    private fun loadMatchData() {
        launchWithResult(
            action = {
                val params = MatchMapper.toMatchParams(uiState.value)
                getMatchedMovies(
                    page = 1,
                    genres = params.genres,
                    runtimeGte = params.runtimeGte,
                    runtimeLte = params.runtimeLte,
                    releaseDateGte = params.releaseDateGte,
                    releaseDateLte = params.releaseDateLte
                )
            },
            onSuccess = { onLoadMatchDataSuccess(it) },
            onError = { onLoadMatchDataError(it) }
        )
    }

    private fun onLoadMatchDataSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                matchResults = movies.map { movie ->
                    MatchMapper.toUiState(
                        movie = movie,
                        genres = uiState.value.movieGenre
                    )
                },
                isLoadingRecommendations = false,
                currentPage = MatchPages.ResultsPage
            )
        }
    }

    private fun onLoadMatchDataError(errorMessage: Int) {
        updateState {
            it.copy(
                isLoadingRecommendations = false,
                errorMessage = errorMessage
            )
        }
    }

    override fun onAnswerSelected(type: QuestionType, answer: QuestionUiState) {
        updateState { state ->
            when (type) {
                QuestionType.MOOD -> state.copy(
                    moodQuestions = state.moodQuestions.map {
                        if (it.id == answer.id) it.copy(isSelected = !it.isSelected) else it
                    }
                )

                QuestionType.GENRE -> state.copy(
                    genreQuestions = state.genreQuestions.map {
                        if (it.id == answer.id) it.copy(isSelected = !it.isSelected) else it
                    }
                )

                QuestionType.TIME -> state.copy(
                    timeQuestions = state.timeQuestions.map {
                        if (it.id == answer.id) it.copy(isSelected = !it.isSelected) else
                            it.copy(isSelected = false)
                    }
                )

                QuestionType.TYPE -> state.copy(
                    movieTypeQuestions = state.movieTypeQuestions.map {
                        if (it.id == answer.id) it.copy(isSelected = !it.isSelected) else
                            it.copy(isSelected = false)
                    }
                )
            }
        }
    }

    override fun onNavigateBack() {
        when (uiState.value.currentPage) {
            MatchPages.QuestionsPage -> {
                updateState { state ->
                    state.copy(
                        currentPage = if (state.currentQuestionType == QuestionType.MOOD)
                            MatchPages.StartPage
                        else
                            MatchPages.QuestionsPage,
                        currentQuestionType = QuestionType.entries[state.currentQuestionType.ordinal.minus(
                            1
                        ).coerceAtLeast(0)]
                    )
                }
            }

            MatchPages.ResultsPage -> updateState {
                it.copy(
                    currentPage = MatchPages.StartPage,
                    currentQuestionType = QuestionType.MOOD,
                    moodQuestions = getMoodQuestionAnswers(),
                    genreQuestions = getGenreQuestionAnswers(),
                    timeQuestions = getTimeQuestionAnswers(),
                    movieTypeQuestions = getMovieTypeQuestionAnswers()
                )
            }

            else -> {}
        }
    }

    override fun onMovieClick(id: Int) {
        sendEvent(MatchEvent.OnMovieClick(id = id))
    }

    override fun onSaveClick(id: Int) {
        sendEvent(MatchEvent.AddToCollection(id = id))
    }

    override fun onPlayClick(id: Int, url: String) {
        updateState { it.copy(isLoading = true) }
        launchWithResult(
            action = { getMovieDetailsUseCase(id) },
            onSuccess = {
                updateState { state -> state.copy(isLoading = false) }
                sendEvent(MatchEvent.OpenTrailer(url = it.trailerUrl))
            },
            onError = { updateState { it.copy(isLoading = false) } }
        )
    }
}