package com.moscow.cineverse.screen.home

import com.moscow.cinverse.presentation.R

enum class HomeFeaturedCollections(val title: Int, val image: Int, val genreId: Int) {
    HORROR(
        title = R.string.late_night_thrills,
        image = R.drawable.horror,
        genreId = 27
    ),
    SCIENCE_FICTION(
        title = R.string.mind_bending_stories,
        image = R.drawable.history,
        genreId = 878
    ),
    DRAMA(
        title = R.string.cinematic_masterpieces,
        image = R.drawable.drama,
        genreId = 18
    ),
    FAMILY(
        title = R.string.family_night_picks,
        image = R.drawable.family,
        genreId = 10751
    ),
    HISTORY(
        title = R.string.based_on_true_events,
        image = R.drawable.science_fiction,
        genreId = 36
    ),
    COMEDY(
        title = R.string.feel_good_favorites,
        image = R.drawable.comedy,
        genreId = 35
    )
}