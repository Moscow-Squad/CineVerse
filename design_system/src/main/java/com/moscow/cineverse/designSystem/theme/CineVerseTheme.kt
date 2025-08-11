package com.moscow.cineverse.designSystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.moscow.cineverse.designSystem.color.LocalCineVerseColors
import com.moscow.cineverse.designSystem.color.darkThemeColor
import com.moscow.cineverse.designSystem.color.lightThemeColor
import com.moscow.cineverse.designSystem.radius.CineVerseRadius
import com.moscow.cineverse.designSystem.radius.LocalCineVerseRadius
import com.moscow.cineverse.designSystem.typography.DefaultTextStyle
import com.moscow.cineverse.designSystem.typography.LocalCineVerseTextStyle

@Composable
fun CineVerseTheme(
    isDark: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDark) darkThemeColor else lightThemeColor

    CompositionLocalProvider(
        LocalCineVerseColors provides colorScheme,
        LocalCineVerseTextStyle provides DefaultTextStyle,
        LocalCineVerseRadius provides CineVerseRadius(),
    ) {
        content()
    }
}