package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import color.CineVerseColor
import color.LocalCineVerseColors
import color.darkThemeColor
import color.lightThemeColor
import icon.CineVerseIcons
import icon.LocalCineVerseIcons
import radius.CineVerseRadius
import radius.LocalCineVerseRadius
import typography.CineVerseTextStyle
import typography.DefaultTextStyle
import typography.LocalCineVerseTextStyle

@Composable
fun CineVerseTheme(
    state: ThemeState = ThemeState(isDark = isSystemInDarkTheme(), onThemeChanged = {}),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        state.isDark -> darkThemeColor
        else -> lightThemeColor
    }
    CompositionLocalProvider(
        LocalThemeState provides state,
        LocalCineVerseIcons provides CineVerseIcons(),
        LocalCineVerseColors provides colorScheme,
        LocalCineVerseTextStyle provides DefaultTextStyle,
        LocalCineVerseRadius provides CineVerseRadius(),
    ) {
        content()
    }
}

object Theme {
    val state: ThemeState
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeState.current

    val icons: CineVerseIcons
        @Composable
        @ReadOnlyComposable
        get() = LocalCineVerseIcons.current

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

internal val LocalThemeState = compositionLocalOf { ThemeState(false) {} }