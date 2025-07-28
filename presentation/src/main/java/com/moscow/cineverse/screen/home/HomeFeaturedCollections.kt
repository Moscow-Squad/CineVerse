package com.moscow.cineverse.screen.home

import com.moscow.cinverse.presentation.R

enum class HomeFeaturedCollections(val title: Int, val image: Int) {
    HORROR(title = R.string.late_night_thrills, image = R.drawable.horror),
    FAMILY(title = R.string.family_night_picks, image = R.drawable.family),
    SCIENCE_FICTION(title = R.string.mind_bending_stories, image = R.drawable.science_fiction),
    HISTORY(title = R.string.based_on_true_events, image = R.drawable.history),
    DRAMA(title = R.string.cinematic_masterpieces, image = R.drawable.drama),
    COMEDY(title = R.string.feel_good_favorites, image = R.drawable.comedy)
}