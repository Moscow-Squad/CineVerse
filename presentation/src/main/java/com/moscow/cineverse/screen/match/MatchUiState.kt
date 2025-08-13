package com.moscow.cineverse.screen.match

import androidx.annotation.DrawableRes
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cinverse.presentation.R
import com.moscow.domain.model.MediaType

data class MatchUiState(
    val isLoading: Boolean = false,
    val currentPage: MatchPages = MatchPages.StartPage,
    val moodQuestions: List<QuestionUiState> = getFakeQuestions(),
    val genres: List<QuestionUiState> = getFakeGenres(),
    val timeQuestions: List<QuestionUiState> = getFakeQuestions(),
    val recentOrClassicQuestions: List<QuestionUiState> = getFakeQuestions(),
    val currentQuestionIndex: Int = 1,
    val matchProgress: Float = 0f,
    val matchResults: List<MediaItemUiState> = getFakeMatchResults()
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

private fun getFakeMatchResults() = listOf(
    MediaItemUiState(
        id = 1311031,
        title = "Demon Slayer: Kimetsu no Yaiba — Infinity Castle",
        posterPath = "https://image.tmdb.org/t/p/w500//aFRDH3P7TX61FVGpaLhKr6QiOC1.jpg",
        rating = 7.2f,
        genres = listOf("Animation", "Action", "Fantasy", "Thriller"),
        releaseDate = "2025, Jul 18",
        duration = "",
        mediaType = MediaType.Movie,
        backdropPath = "https://image.tmdb.org/t/p/w500//1RgPyOhN4DRs225BGTlHJqCudII.jpg"
    ),
    MediaItemUiState(
        id = 980477,
        title = "Ne Zha 2",
        posterPath = "https://image.tmdb.org/t/p/w500//293Mo4GWf7Tl0TfAr5NFghqeMy7.jpg",
        rating = 8.038f,
        genres = listOf("Animation", "Fantasy", "Adventure", "Action"),
        releaseDate = "2025, Jan 29",
        duration = "",
        mediaType = MediaType.Movie,
        backdropPath = "https://image.tmdb.org/t/p/w500//c9xmB53umjnrCMS4pZz11clF3yJ.jpg"
    ),
    MediaItemUiState(
        id = 1078605,
        title = "Weapons",
        posterPath = "https://image.tmdb.org/t/p/w500//yrNqjlabBhEpB5tFCysWtnMx5C5.jpg",
        rating = 7.8f,
        genres = listOf("Horror", "Mystery"),
        releaseDate = "2025, Aug 06",
        duration = "",
        mediaType = MediaType.Movie,
        backdropPath = "https://image.tmdb.org/t/p/w500//kyqM6padQzZ1eYxv84i9smNvZAG.jpg"
    ),
    MediaItemUiState(
        id = 1011477,
        title = "Karate Kid: Legends",
        posterPath = "https://image.tmdb.org/t/p/w500//AEgggzRr1vZCLY86MAp93li43z.jpg",
        rating = 7.162f,
        genres = listOf("Action", "Adventure", "Drama"),
        releaseDate = "2025, May 08",
        duration = "",
        mediaType = MediaType.Movie,
        backdropPath = "https://image.tmdb.org/t/p/w500//7Q2CmqIVJuDAESPPp76rWIiA0AD.jpg"
    ),
    MediaItemUiState(
        id = 541671,
        title = "Ballerina",
        posterPath = "https://image.tmdb.org/t/p/w500//2VUmvqsHb6cEtdfscEA6fqqVzLg.jpg",
        rating = 7.436f,
        genres = listOf("Action", "Thriller", "Crime"),
        releaseDate = "2025, Jun 04",
        duration = "",
        mediaType = MediaType.Movie,
        backdropPath = "https://image.tmdb.org/t/p/w500//oPgXVSdGR9dGwbmvIToOCMmsdc2.jpg"
    ),
    MediaItemUiState(
        id = 803796,
        title = "KPop Demon Hunters",
        posterPath = "https://image.tmdb.org/t/p/w500//22AouvwlhlXbe3nrFcjzL24bvWH.jpg",
        rating = 8.4f,
        genres = listOf("Animation", "Fantasy", "Comedy", "Music", "Family"),
        releaseDate = "2025, Jun 20",
        duration = "",
        mediaType = MediaType.Movie,
        backdropPath = "https://image.tmdb.org/t/p/w500//l3ycQYwWmbz7p8otwbomFDXIEhn.jpg"
    ),
    MediaItemUiState(
        id = 1124619,
        title = "Bride Hard",
        posterPath = "https://image.tmdb.org/t/p/w500//3mExdWLSxAiUCb4NMcYmxSkO7n4.jpg",
        rating = 5.746f,
        genres = listOf("Action", "Comedy"),
        releaseDate = "2025, Jun 19",
        duration = "",
        mediaType = MediaType.Movie,
        backdropPath = "https://image.tmdb.org/t/p/w500//2DcD4Hh80SW7YVpwckkiEFRZX06.jpg"
    )
)