package com.moscow.cineverse.screen.match

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.moscow.cineverse.screen.details.movie_details.MovieScreenState
import com.moscow.cineverse.screen.explore.ExploreScreenState
import com.moscow.cinverse.presentation.R

data class MatchUiState(
    val isLoading: Boolean = false,
    val currentPage: MatchPages = MatchPages.StartPage,
    val moodQuestions: List<QuestionUiState> = getMoodQuestionAnswers(),
    val genreQuestions: List<QuestionUiState> = getGenreQuestionAnswers(),
    val timeQuestions: List<QuestionUiState> = getTimeQuestionAnswers(),
    val movieTypeQuestions: List<QuestionUiState> = getMovieTypeQuestionAnswers(),
    val currentQuestionType: QuestionType = QuestionType.MOOD,
    val movieGenre: List<ExploreScreenState.GenreUiState> = emptyList(),
    val matchResults: List<MovieScreenState.MovieDetailsUiState> = emptyList(),
    val isLoadingRecommendations: Boolean = false,
    val errorMessage: Int? = null,
    val enableBlur: String = "high",
    val shouldShowError: Boolean = false
) {
    val matchProgress: Float =
        currentQuestionType.ordinal.plus(1).toFloat() / QuestionType.entries.size
    val isNextButtonActivated: Boolean = when (currentQuestionType) {
        QuestionType.MOOD -> moodQuestions.any { it.isSelected }
        QuestionType.GENRE -> genreQuestions.any { it.isSelected }
        QuestionType.TIME -> timeQuestions.any { it.isSelected }
        QuestionType.TYPE -> movieTypeQuestions.any { it.isSelected }
    }
    val selectedMoodQuestions: List<QuestionUiState>
        get() = moodQuestions.filter { it.isSelected }


    val selectedGenres: List<QuestionUiState>
        get() = genreQuestions.filter { it.isSelected }

    val selectedTimeQuestion: List<QuestionUiState>
        get() = timeQuestions.filter { it.isSelected }

    val selectedMovieTypeQuestion: List<QuestionUiState>
        get() = movieTypeQuestions.filter { it.isSelected }

}

data class QuestionUiState(
    val id: Int,
    @StringRes val name: Int,
    @StringRes val description: Int? = null,
    val isSelected: Boolean = false,
    @DrawableRes val iconResource: Int? = null,
)

fun getMoodQuestionAnswers() = listOf(
    QuestionUiState(
        id = 1,
        name = R.string.mood_chill,
        iconResource = R.drawable.headphone_icon
    ),
    QuestionUiState(
        id = 2,
        name = R.string.mood_excited,
        iconResource = R.drawable.flame_icon
    ),
    QuestionUiState(
        id = 3,
        name = R.string.mood_emotional,
        iconResource = R.drawable.heart_icon
    ),
    QuestionUiState(
        id = 4,
        name = R.string.mood_curious,
        iconResource = R.drawable.due_tone_search
    )
)

fun getMovieTypeQuestionAnswers() = listOf(
    QuestionUiState(
        id = 1,
        name = R.string.recent,
    ),
    QuestionUiState(
        id = 2,
        name = R.string.classic,
    ),
    QuestionUiState(
        id = 3,
        name = R.string.both,
    )
)

fun getTimeQuestionAnswers() = listOf(
    QuestionUiState(
        id = 1,
        name = R.string.time_short_label,
        description = R.string.time_short_desc,
        iconResource = R.drawable.time_short_icon
    ),
    QuestionUiState(
        id = 2,
        name = R.string.time_medium_label,
        description = R.string.time_medium_desc,
        iconResource = R.drawable.time_medium_icon
    ),
    QuestionUiState(
        id = 3,
        name = R.string.time_long_label,
        description = R.string.time_long_desc,
        iconResource = R.drawable.time_long_icon
    )
)

fun getGenreQuestionAnswers() = listOf(
    QuestionUiState(id = 1, name = R.string.genre_action),
    QuestionUiState(id = 2, name = R.string.genre_comedy),
    QuestionUiState(id = 3, name = R.string.genre_drama),
    QuestionUiState(id = 4, name = R.string.genre_romance),
    QuestionUiState(id = 5, name = R.string.genre_scifi),
    QuestionUiState(id = 6, name = R.string.genre_thriller),
    QuestionUiState(id = 7, name = R.string.genre_animation),
    QuestionUiState(id = 8, name = R.string.genre_mystery)
)