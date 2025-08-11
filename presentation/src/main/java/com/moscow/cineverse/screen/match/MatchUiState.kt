package com.moscow.cineverse.screen.match

import androidx.annotation.DrawableRes
import com.moscow.cinverse.presentation.R

data class MatchUiState(
    val isLoading: Boolean = false,
    val currentPage: MatchPages = MatchPages.StartPage,
    val moodQuestions: List<QuestionUiState> = getFakeQuestions(),
    val genres: List<QuestionUiState> = getFakeGenres(),
    val timeQuestions: List<QuestionUiState> = getFakeQuestions(),
    val recentOrClassicQuestions: List<QuestionUiState> = getFakeQuestions(),
    val currentQuestionIndex: Int = 1,
    val matchProgress: Float = 0f,
) {
    val selectedMoodQuestions: List<QuestionUiState>
        get() = moodQuestions.filter { it.isSelected }


    val selectedGenres: List<QuestionUiState>
        get() = genres.filter { it.isSelected }

    val selectedTimeQuestion: QuestionUiState
        get() = timeQuestions.first { it.isSelected }

}

data class MatchQuestion(
    val id: Int,
    val question: Int,
    val answers: List<Int>,
    val selectedAnswers: List<Int> = emptyList(),
)

private fun getFakeQuestions() = listOf(
    QuestionUiState(
        id = 1,
        name = "What mood are you in?",
        iconResource = R.drawable.cine_verse_logo_splash
    ),
    QuestionUiState(
        id = 2,
        name = "Pick a genre",
        iconResource = com.moscow.cineverse.design_system.R.drawable.colored_cineverse_logo
    ),
    QuestionUiState(
        id = 3,
        name = "How much time do you have?",
        iconResource = R.drawable.folder_icon
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