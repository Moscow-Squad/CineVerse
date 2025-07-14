package com.moscow.cineverse.screen.cast_details_show_all

import com.moscow.cineverse.designSystem.component.MovieUI
import com.moscow.cineverse.designSystem.component.ViewMode

data class ShowAllActorMoviesState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val viewMode: ViewMode = ViewMode.GRID,
    val moviesUI: List<MovieUI> = List(20) { index ->
        MovieUI(
            id = index,
            title = "Movie $index",
            posterUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Christian_Bale-7837.jpg/330px-Christian_Bale-7837.jpg",
            rating = 7.5f,
            genres = listOf("Action", "Adventure"),
            duration = "2h 30m",
            releaseDate = "2023-10-01"
        )
    }
)

