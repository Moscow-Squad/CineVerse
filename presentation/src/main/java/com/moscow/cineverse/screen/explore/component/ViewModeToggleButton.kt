package com.moscow.cineverse.screen.explore.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.ViewModeToggle

@Composable
fun ViewModeToggleButton(
    selectedMode: ViewMode,
    onModeSelected: (ViewMode) -> Unit,
    modifier: Modifier = Modifier
) {
    ViewModeToggle(
        selectedMode = selectedMode,
        onModeSelected = onModeSelected,
        modifier = modifier
    )
}
