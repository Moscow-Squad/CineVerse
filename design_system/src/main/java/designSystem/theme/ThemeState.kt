package designSystem.theme

import androidx.compose.runtime.Stable

@Stable
data class ThemeState(
    val isDark: Boolean,
    val onThemeChanged: (Boolean) -> Unit
)