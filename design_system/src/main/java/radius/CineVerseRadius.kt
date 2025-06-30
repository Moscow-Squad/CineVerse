package radius

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CineVerseRadius(
    val none: Dp = 0.dp,
    val extraExtraSmall: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 10.dp,
    val large: Dp = 12.dp,
    val extraLarge: Dp = 16.dp,
    val extraExtraLarge: Dp = 20.dp,
    val extraExtraExtraLarge: Dp = 24.dp,
    val size4xl: Dp = 28.dp,
    val size5xl: Dp = 32.dp,
    val full: Dp = 1000.dp
)

internal val LocalCineVerseRadius = staticCompositionLocalOf { CineVerseRadius() }