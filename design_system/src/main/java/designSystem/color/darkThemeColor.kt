package designSystem.color

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val darkThemeColor = CineVerseColor(
    background = Background(
        screen = Color(0xFF121321),
        card = Color(0xFF1B1C2A),
        bottomSheet = Color(0xFF1B1C2A),
        bottomSheetCard = Color(0xFF242533)
    ),
    shade = Shade(
        primary = Color(0xFFE1E1E3),
        secondary = Color(0xFFA4A4AA),
        tertiary = Color(0xFF72727B),
        quaternary = Color(0xFF2D2E3B),
        quinary = Color(0xFF242533)
    ),
    brand = Brand(
        primary = Color(0xFF8C9EFF),
        secondary = Color(0xFF464D7B),
        tertiary = Color(0xFF24263B),
    ),
    button = Button(
        primary = Color(0xFF8C9EFF),
        secondary = Color(0xFF24263B),
        disabled = Color(0xFF242533),
        onPrimary = Color(0xFF1B1C2A),
        onSecondary = Color(0xFFE1E1E3),
        onDisabled = Color(0xFF72727B),
        onTertiary = Color(0xFF8C9EFF)
    ),
    stroke = Stroke(
        primary = Color(0xFF24263B),
        glow = Brush.linearGradient(
            colors = listOf(
                Color(0xFF8C9EFF).copy(alpha = 0.24f),
                Color(0xFF8C9EFF),
                Color(0xFF8C9EFF),
                Color(0xFF8C9EFF).copy(alpha = 0.22f)
            )
        )
    ),
    overlay = Overlay(
        primary = Color(0xFF121321).copy(alpha = 0.60f),
        secondary = Color(0xFF121321).copy(alpha = 0.24f),
    ),
    additional = Additional(
        primary = Primary(
            red = Color(0xFFFF6B6B),
            green = Color(0xFF00E676),
            yellow = Color(0xFFFFD600)
        ),
        secondary = Secondary(
            red = Color(0xFF2C202D),
            green = Color(0xFF232A31),
            yellow = Color(0xFF2D2927)
        )
    )
)