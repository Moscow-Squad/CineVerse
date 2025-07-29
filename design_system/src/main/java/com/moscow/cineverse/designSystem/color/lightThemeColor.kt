package com.moscow.cineverse.designSystem.color

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val lightThemeColor = CineVerseColor(
    background = Background(
        screen = Color(0xFFF7F7F7),
        card = Color(0xFFFFFFFF),
        bottomSheet = Color(0xFFFFFFFF),
        bottomSheetCard = Color(0xFFF6F6F6),
        bottomSheetContainer = Color(0x12121299)
    ),
    shade = Shade(
        primary = Color(0xFF313131),
        secondary = Color(0xFF717171),
        tertiary = Color(0xFFA5A5A5),
        quaternary = Color(0xFFECECEC),
        quinary = Color(0xFFF6F6F6)
    ),
    brand = Brand(
        primary = Color(0xFF536DFE),
        secondary = Color(0xFFBEC8FF),
        tertiary = Color(0xFFF1F3FF),
    ),
    button = Button(
        primary = Color(0xFF536DFE),
        secondary = Color(0xFFF1F3FF),
        disabled = Color(0xFFE5E5E5),
        onPrimary = Color(0xFFFFFFFF),
        onSecondary = Color(0xFF313131),
        onDisabled = Color(0xFFA5A5A5),
        onTertiary = Color(0xFF536DFE)
    ),
    stroke = Stroke(
        primary = Color(0xFFF1F3FF),
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
        primary = Color(0xFFFFFFFF).copy(alpha = 0.60f),
        secondary = Color(0xFFFFFFFF).copy(alpha = 0.24f),
    ),
    additional = Additional(
        primary = Primary(
            red = Color(0xFFEF4444),
            green = Color(0xFF22C55E),
            yellow = Color(0xFFFACC15)
        ),
        secondary = Secondary(
            red = Color(0xFFFBEEEE),
            green = Color(0xFFEEF5EF),
            yellow = Color(0xFFFFFAEB)
        )
    )
)