package com.moscow.cineverse.designSystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.moscow.cineverse.designSystem.color.CineVerseColor
import com.moscow.cineverse.designSystem.color.LocalCineVerseColors
import com.moscow.cineverse.designSystem.radius.CineVerseRadius
import com.moscow.cineverse.designSystem.radius.LocalCineVerseRadius
import com.moscow.cineverse.designSystem.typography.CineVerseTextStyle
import com.moscow.cineverse.designSystem.typography.LocalCineVerseTextStyle

object Theme {
    val colors: CineVerseColor
        @Composable
        @ReadOnlyComposable
        get() = LocalCineVerseColors.current

    val textStyle: CineVerseTextStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalCineVerseTextStyle.current

    val radius: CineVerseRadius
        @Composable
        @ReadOnlyComposable
        get() = LocalCineVerseRadius.current
}