package com.moscow.cineverse.screen.match

import androidx.annotation.DrawableRes
import com.moscow.cineverse.screen.details.movie_details.MovieScreenState
import com.moscow.cineverse.screen.explore.ExploreScreenState
import com.moscow.cinverse.presentation.R
import kotlinx.datetime.LocalDate
import kotlin.collections.filter

data class MatchUiState(
    val isLoading: Boolean = false,
    val currentPage: MatchPages = MatchPages.StartPage,
    val moodQuestions: List<QuestionUiState> = getFakeMoods(),
    val genreQuestions: List<QuestionUiState> = getFakeGenres(),
    val timeQuestions: List<QuestionUiState> = getFakeTimeQuestions(),
    val movieTypeQuestions: List<QuestionUiState> = getFakeMovieTypes(),
    val currentQuestionType: QuestionType = QuestionType.MOOD,
    val movieGenre: List<ExploreScreenState.GenreUiState> = emptyList(),
    val matchResults: List<MovieScreenState.MovieDetailsUiState> = emptyList(),
    val isLoadingRecommendations: Boolean = false,
    val errorMessage: Int? = null,
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

data class MatchQuestion(
    val id: Int,
    val question: Int,
    val answers: List<Int>,
    val selectedAnswers: List<Int> = emptyList(),
)

private fun getFakeMoods() = listOf(
    QuestionUiState(
        id = 1,
        name = "Chill",
        iconResource = R.drawable.headphone_icon
    ),
    QuestionUiState(
        id = 2,
        name = "Excited",
        iconResource = R.drawable.flame_icon
    ),
    QuestionUiState(
        id = 3,
        name = "Emotional",
        iconResource = R.drawable.heart_icon
    ),
    QuestionUiState(
        id = 4,
        name = "Curious",
        iconResource = R.drawable.due_tone_search
    )
)

private fun getFakeMovieTypes() = listOf(
    QuestionUiState(
        id = 1,
        name = "Recent",
        iconResource = R.drawable.cine_verse_logo_splash
    ),
    QuestionUiState(
        id = 2,
        name = "Classic",
        iconResource = com.moscow.cineverse.design_system.R.drawable.colored_cineverse_logo
    ),
    QuestionUiState(
        id = 3,
        name = "Both",
        iconResource = R.drawable.folder_icon
    )
)

private fun getFakeTimeQuestions() = listOf(
    QuestionUiState(
        id = 1,
        name = "Short",
        description = "under 90 min",
        iconResource = R.drawable.time_short_icon
    ),
    QuestionUiState(
        id = 2,
        name = "Medium",
        description = "between 90 & 120 min",
        iconResource = R.drawable.time_medium_icon
    ),
    QuestionUiState(
        id = 3,
        name = "Long",
        description = "more than 120 min",
        iconResource = R.drawable.time_long_icon
    )
)

private fun getFakeGenres() = listOf(
    QuestionUiState(id = 1, name = "Action"),
    QuestionUiState(id = 2, name = "Comedy"),
    QuestionUiState(id = 3, name = "Drama"),
    QuestionUiState(id = 4, name = "Romance"),
    QuestionUiState(id = 5, name = "Sci-Fi"),
    QuestionUiState(id = 6, name = "Thriller"),
    QuestionUiState(id = 7, name = "Animation"),
    QuestionUiState(id = 8, name = "Mystery")
)

data class QuestionUiState(
    val id: Int,
    val name: String,
    val description: String? = null,
    val isSelected: Boolean = false,
    @DrawableRes val iconResource: Int? = null,
)

fun getDefaultMatchQuestions(): List<MatchQuestion> {
    return listOf(
        MatchQuestion(
            id = 1,
            question = R.string.what_mood,
            answers = listOf(
                R.string.mood_chill,
                R.string.mood_excited,
                R.string.mood_emotional,
                R.string.mood_curious
            )
        ),
        MatchQuestion(
            id = 2,
            question = R.string.pick_genre,
            answers = listOf(
                R.string.genre_action,
                R.string.genre_comedy,
                R.string.genre_drama,
                R.string.genre_romance,
                R.string.genre_scifi,
                R.string.genre_thriller,
                R.string.genre_animation,
                R.string.genre_mystery
            )
        ),
        MatchQuestion(
            id = 3,
            question = R.string.how_much_time,
            answers = listOf(
                R.string.time_short,
                R.string.time_medium,
                R.string.time_long
            )
        ),
        MatchQuestion(
            id = 4,
            question = R.string.recent_or_classic,
            answers = listOf(
                R.string.recent,
                R.string.classic,
                R.string.both
            )
        )
    )
}

