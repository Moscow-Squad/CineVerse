package com.moscow.cineverse.designSystem.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

enum class ViewType {
    GRID,
    LIST
}

@Composable
fun CineVerseViewToggle(
    selectedView: ViewType,
    onViewTypeChange: (ViewType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Theme.colors.shade.quaternary,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Theme.colors.background.card)
    ) {
        // Grid View Button with custom icon
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { onViewTypeChange(ViewType.GRID) }
                .background(
                    if (selectedView == ViewType.GRID) Theme.colors.brand.primary
                    else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            GridIcon(
                isSelected = selectedView == ViewType.GRID
            )
        }

        // Divider
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(Theme.colors.shade.quaternary)
        )

        // List View Button with custom icon
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { onViewTypeChange(ViewType.LIST) }
                .background(
                    if (selectedView == ViewType.LIST) Theme.colors.brand.primary
                    else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            ListIcon(
                isSelected = selectedView == ViewType.LIST
            )
        }
    }
}

@Composable
private fun GridIcon(
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val color = if (isSelected) Color.White else Theme.colors.shade.secondary

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(color, RoundedCornerShape(1.5.dp))
            )
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(color, RoundedCornerShape(1.5.dp))
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(color, RoundedCornerShape(1.5.dp))
            )
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(color, RoundedCornerShape(1.5.dp))
            )
        }
    }
}

@Composable
private fun ListIcon(
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val color = if (isSelected) Color.White else Theme.colors.shade.secondary

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Box(
            modifier = Modifier
                .width(18.dp)
                .height(3.dp)
                .background(color, RoundedCornerShape(1.5.dp))
        )
        Box(
            modifier = Modifier
                .width(18.dp)
                .height(3.dp)
                .background(color, RoundedCornerShape(1.5.dp))
        )
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun CineVerseViewTogglePreview() {
    var selectedView by remember { mutableStateOf(ViewType.GRID) }

    CineVerseTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "View Toggle Component",
                    style = MaterialTheme.typography.titleMedium
                )

                CineVerseViewToggle(
                    selectedView = selectedView,
                    onViewTypeChange = { selectedView = it }
                )

                // All states preview
                Text(
                    "All States",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Grid Selected", style = MaterialTheme.typography.bodySmall)
                        CineVerseViewToggle(
                            selectedView = ViewType.GRID,
                            onViewTypeChange = { },
                            modifier = Modifier.width(100.dp)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("List Selected", style = MaterialTheme.typography.bodySmall)
                        CineVerseViewToggle(
                            selectedView = ViewType.LIST,
                            onViewTypeChange = { },
                            modifier = Modifier.width(100.dp)
                        )
                    }
                }
            }
        }
    }
}

// Preview showing the exact layout from your image
@Preview(name = "Image Layout", showBackground = true, backgroundColor = 0xFF1C1C1C)
@Composable
private fun CineVerseViewToggleImageLayout() {
    CineVerseTheme {
        Box(
            modifier = Modifier
                .background(Color(0xFF1C1C1C))
                .padding(32.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Top left - Grid selected
                    CineVerseViewToggle(
                        selectedView = ViewType.GRID,
                        onViewTypeChange = { },
                        modifier = Modifier.size(80.dp)
                    )

                    // Top right - Grid selected (showing both buttons)
                    CineVerseViewToggle(
                        selectedView = ViewType.GRID,
                        onViewTypeChange = { },
                        modifier = Modifier.size(80.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Bottom left - List selected
                    CineVerseViewToggle(
                        selectedView = ViewType.LIST,
                        onViewTypeChange = { },
                        modifier = Modifier.size(80.dp)
                    )

                    // Bottom right - List selected
                    CineVerseViewToggle(
                        selectedView = ViewType.LIST,
                        onViewTypeChange = { },
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }
    }
}