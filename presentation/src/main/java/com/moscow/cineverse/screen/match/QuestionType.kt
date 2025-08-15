package com.moscow.cineverse.screen.match

import androidx.annotation.StringRes
import com.moscow.cinverse.presentation.R

enum class QuestionType(@StringRes val titleResource: Int) {
    MOOD(R.string.what_mood),
    GENRE(R.string.pick_genre),
    TIME(R.string.how_much_time),
    TYPE(R.string.recent_or_classic)
}