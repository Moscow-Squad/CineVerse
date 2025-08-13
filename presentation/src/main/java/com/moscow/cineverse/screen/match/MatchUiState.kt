package com.moscow.cineverse.screen.match

import androidx.annotation.DrawableRes
import com.moscow.cineverse.screen.movie_details.MovieScreenState
import com.moscow.cinverse.presentation.R
import kotlinx.datetime.LocalDate

data class MatchUiState(
    val isLoading: Boolean = false,
    val currentPage: MatchPages = MatchPages.ResultsPage,
    val moodQuestions: List<QuestionUiState> = getFakeQuestions(),
    val genres: List<QuestionUiState> = getFakeGenres(),
    val timeQuestions: List<QuestionUiState> = getFakeQuestions(),
    val recentOrClassicQuestions: List<QuestionUiState> = getFakeQuestions(),
    val currentQuestionIndex: Int = 1,
    val matchProgress: Float = 0f,
    val matchResults: List<MovieScreenState.MovieDetailsUiState> = getFakeMatchResults()
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
    MovieScreenState.MovieDetailsUiState(
        id = 1311031,
        title = "Demon Slayer: Kimetsu no Yaiba — Infinity Castle",
        trailerPath = "Demon Slayer: Kimetsu no Yaiba Infinity Castle | Official ...Demon Slayer: Kimetsu no Yaiba Infinity Castle | Official ...Demon Slayer: Kimetsu no Yaiba Infinity Castle | Official ...Demon Slayer: Kimetsu no Yaiba Infinity Castle | Official ...Demon Slayer: Kimetsu no Yaiba Infinity Castle | Official ...https://www.youtube.com/watch?v=wyiZWYMilgk",
        posterPath = "https://image.tmdb.org/t/p/w500//aFRDH3P7TX61FVGpaLhKr6QiOC1.jpg",
        rating = 7.2,
        genres = listOf("Animation", "Action", "Fantasy", "Thriller"),
        releaseDate = LocalDate(2025, 7, 18),
        duration = 120,
        description = "The Hashira and Tanjiro prepare to face Muzan Kibutsuji in the Infinity Castle."
    ),
    MovieScreenState.MovieDetailsUiState(
        id = 980477,
        title = "Ne Zha 2",
        trailerPath = "https://www.youtube.com/watch?v=wyiZWYMilgk",
        posterPath = "https://image.tmdb.org/t/p/w500//293Mo4GWf7Tl0TfAr5NFghqeMy7.jpg",
        rating = 8.038,
        genres = listOf("Animation", "Fantasy", "Adventure", "Action"),
        releaseDate = LocalDate(2025, 1, 29),
        duration = 115,
        description = "The continuing adventures of the mythological hero Ne Zha."
    ),
    MovieScreenState.MovieDetailsUiState(
        id = 1078605,
        title = "Weapons",
        trailerPath = "YouTube · CrunchyrollYouTube · CrunchyrollYouTube · CrunchyrollYouTube · CrunchyrollYouTube · Crunchyrollhttps://www.youtube.com/watch?v=wyiZWYMilgk",
        posterPath = "https://image.tmdb.org/t/p/w500//yrNqjlabBhEpB5tFCysWtnMx5C5.jpg",
        rating = 7.8,
        genres = listOf("Horror", "Mystery"),
        releaseDate = LocalDate(2025, 8, 6),
        duration = 135,
        description = "A suspenseful horror mystery that will keep you on the edge of your seat."
    ),
    MovieScreenState.MovieDetailsUiState(
        id = 1011477,
        title = "Karate Kid: Legends",
        trailerPath = "أكثر من ١٧٫٧ مليون مشاهدة · قبل سنة واحدةأكثر من ١٧٫٧ مليون مشاهدة · قبل سنة واحدةأكثر من ١٧٫٧ مليون مشاهدة · قبل سنة واحدةأكثر من ١٧٫٧ مليون مشاهدة · قبل سنة واحدةأكثر من ١٧٫٧ مليون مشاهدة · قبل سنة واحدةhttps://www.youtube.com/watch?v=wyiZWYMilgk",
        posterPath = "https://image.tmdb.org/t/p/w500//AEgggzRr1vZCLY86MAp93li43z.jpg",
        rating = 7.162,
        genres = listOf("Action", "Adventure", "Drama"),
        releaseDate = LocalDate(2025, 5, 8),
        duration = 130,
        description = "A new chapter in the legendary Karate Kid saga."
    ),
    MovieScreenState.MovieDetailsUiState(
        id = 541671,
        title = "Ballerina",
        trailerPath = "https://www.youtube.com/watch?v=wyiZWYMilgk",
        posterPath = "https://image.tmdb.org/t/p/w500//2VUmvqsHb6cEtdfscEA6fqqVzLg.jpg",
        rating = 7.436,
        genres = listOf("Action", "Thriller", "Crime"),
        releaseDate = LocalDate(2025, 6, 4),
        duration = 110,
        description = "A former ballerina turned assassin seeks revenge in the John Wick universe."
    ),
    MovieScreenState.MovieDetailsUiState(
        id = 803796,
        title = "KPop Demon Hunters",
        trailerPath = "https://www.youtube.com/watch?v=wyiZWYMilgk",
        posterPath = "https://image.tmdb.org/t/p/w500//22AouvwlhlXbe3nrFcjzL24bvWH.jpg",
        rating = 8.4,
        genres = listOf("Animation", "Fantasy", "Comedy", "Music", "Family"),
        releaseDate = LocalDate(2025, 6, 20),
        duration = 105,
        description = "A K-pop group discovers they must balance their music careers with hunting demons."
    ),
    MovieScreenState.MovieDetailsUiState(
        id = 1124619,
        title = "Bride Hard",
        trailerPath = "https://www.youtube.com/watch?v=wyiZWYMilgk",
        posterPath = "https://image.tmdb.org/t/p/w500//3mExdWLSxAiUCb4NMcYmxSkO7n4.jpg",
        rating = 5.746,
        genres = listOf("Action", "Comedy"),
        releaseDate = LocalDate(2025, 6, 19),
        duration = 100,
        description = "A bride's wedding day turns into an action-packed adventure."
    )
)