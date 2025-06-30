package designSystem.typography

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

data class CineVerseTextStyle(
    val display: TextStyle,
    val title: TitleStyle,
    val body: BodyAndLabelStyle,
    val label: BodyAndLabelStyle
)

data class TitleStyle(
    val extraLarge: TextStyle,
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle
)

data class BodyAndLabelStyle(
    val large: FontWeightStyle,
    val medium: FontWeightStyle,
    val small: FontWeightStyle
)

data class FontWeightStyle(
    val regular: TextStyle,
    val medium: TextStyle,
    val semiBold: TextStyle
)

internal val LocalCineVerseTextStyle = staticCompositionLocalOf { DefaultTextStyle }