package icon

import androidx.compose.runtime.staticCompositionLocalOf

data class CineVerseIcons (
    val outline: Outline = outlineIcons,
    val bold: Bold = boldIcons,
    val dueTone: DueTone = dueToneIcons,
    val colored: Colored = coloredIcons,
)

internal val localCineVerseIcons = staticCompositionLocalOf { CineVerseIcons() }