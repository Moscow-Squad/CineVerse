package com.moscow.cineverse.designSystem.component

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

enum class LayoutMode {
    GRID, LIST
}

@Composable
fun ViewModeToggle(
    selectedMode: LayoutMode,
    onModeSelected: (LayoutMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.height(56.dp)
    ) {
        // Background with border
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(Theme.radius.small))
                .border(
                    width = 1.dp,
                    color = Theme.colors.stroke.primary,
                    shape = RoundedCornerShape(Theme.radius.small)
                )
                .background(Theme.colors.background.card)
        )

        // Buttons row
        Row(
            modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Grid button
            ViewModeButton(
                isSelected = selectedMode == LayoutMode.GRID,
                onClick = { onModeSelected(LayoutMode.GRID) },
                content = {
                    GridIcon(
                        isSelected = selectedMode == LayoutMode.GRID
                    )
                }
            )

            // List button
            ViewModeButton(
                isSelected = selectedMode == LayoutMode.LIST,
                onClick = { onModeSelected(LayoutMode.LIST) },
                content = {
                    ListIcon(
                        isSelected = selectedMode == LayoutMode.LIST
                    )
                }
            )
        }
    }
}

@Composable
private fun ViewModeButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.brand.tertiary else Color.Transparent,
        label = "backgroundColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.brand.secondary else Color.Transparent,
        label = "borderColor"
    )

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(80.dp)
            .zIndex(if (isSelected) 1f else 0f) // Bring selected button to front
            .clip(RoundedCornerShape(Theme.radius.small))
            .background(backgroundColor)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(Theme.radius.small)
                    )
                } else {
                    Modifier
                }
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun GridIcon(isSelected: Boolean) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.brand.primary else Theme.colors.shade.secondary,
        label = "iconColor"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .then(
                        if (isSelected) {
                            Modifier.background(iconColor)
                        } else {
                            Modifier.border(
                                width = 1.5.dp,
                                color = iconColor,
                                shape = RoundedCornerShape(2.dp)
                            )
                        }
                    )
            )
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .then(
                        if (isSelected) {
                            Modifier.background(iconColor.copy(alpha = 0.4f))
                        } else {
                            Modifier.border(
                                width = 1.5.dp,
                                color = iconColor,
                                shape = RoundedCornerShape(2.dp)
                            )
                        }
                    )
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .then(
                        if (isSelected) {
                            Modifier.background(iconColor.copy(alpha = 0.4f))
                        } else {
                            Modifier.border(
                                width = 1.5.dp,
                                color = iconColor,
                                shape = RoundedCornerShape(2.dp)
                            )
                        }
                    )
            )
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .then(
                        if (isSelected) {
                            Modifier.background(iconColor)
                        } else {
                            Modifier.border(
                                width = 1.5.dp,
                                color = iconColor,
                                shape = RoundedCornerShape(2.dp)
                            )
                        }
                    )
            )
        }
    }
}

@Composable
private fun ListIcon(isSelected: Boolean) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.brand.primary else Theme.colors.shade.secondary,
        label = "iconColor"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(28.dp)
                .height(12.dp)
                .clip(RoundedCornerShape(2.dp))
                .then(
                    if (isSelected) {
                        Modifier.background(iconColor)
                    } else {
                        Modifier.border(
                            width = 1.5.dp,
                            color = iconColor,
                            shape = RoundedCornerShape(2.dp)
                        )
                    }
                )
        )
        Box(
            modifier = Modifier
                .width(28.dp)
                .height(12.dp)
                .clip(RoundedCornerShape(2.dp))
                .then(
                    if (isSelected) {
                        Modifier.background(iconColor.copy(alpha = 0.4f))
                    } else {
                        Modifier.border(
                            width = 1.5.dp,
                            color = iconColor,
                            shape = RoundedCornerShape(2.dp)
                        )
                    }
                )
        )
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ViewModeTogglePreview() {
    var layoutMode by remember { mutableStateOf(LayoutMode.GRID) }
    CineVerseTheme {
        ViewModeToggle(
            selectedMode = layoutMode,
            onModeSelected = { layoutMode = it }
        )
    }
}