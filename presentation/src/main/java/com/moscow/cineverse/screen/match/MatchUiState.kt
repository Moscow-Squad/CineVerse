package com.moscow.cineverse.screen.match

import com.moscow.cinverse.presentation.R

data class MatchUiState(
    val isLoading: Boolean = false,
    val currentPage: MatchPages = MatchPages.StartPage,
    val questions: List<MatchQuestion> = getDefaultMatchQuestions(),
)

data class MatchQuestion(
    val id: Int,
    val question: Int,
    val answers: List<Int>,
    val selectedAnswers: List<Int> = emptyList(),
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