package designSystem.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class CineVerseColor(
    val background: Background,
    val shade: Shade,
    val brand: Brand,
    val button: Button,
    val stroke: Stroke,
    val overlay: Overlay,
    val additional: Additional
)

data class Background(
    val screen: Color,
    val card: Color,
    val bottomSheet: Color,
    val bottomSheetCard: Color
)

data class Shade(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val quaternary: Color,
    val quinary: Color
)

data class Brand(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
)

data class Button(
    val primary: Color,
    val secondary: Color,
    val disabled: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onDisabled: Color,
    val onTertiary: Color
)

data class Stroke(
    val primary: Color,
    val glow: Brush
)

data class Overlay(
    val primary: Color,
    val secondary: Color,
)

data class Additional(
    val primary: Primary,
    val secondary: Secondary
)

data class Primary(
    val red: Color,
    val green: Color,
    val yellow: Color,
)

data class Secondary(
    val red: Color,
    val green: Color,
    val yellow: Color,
)

internal val LocalCineVerseColors = staticCompositionLocalOf { lightThemeColor }