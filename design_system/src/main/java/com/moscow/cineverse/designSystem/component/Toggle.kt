package com.moscow.cineverse.designSystem.component

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme


@Composable
fun ViewModeToggle(
    selectedMode: ViewMode,
    onModeSelected: (ViewMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(1.dp)
            .background(Theme.colors.background.card, shape = RoundedCornerShape(Theme.radius.large))
            .border(
                width = 1.dp,
                color = Theme.colors.stroke.primary,
                shape = RoundedCornerShape(Theme.radius.large)
            )
            .padding(1.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            ViewModeButton(
                isSelected = selectedMode == ViewMode.GRID,
                onClick = { onModeSelected(ViewMode.GRID) },
                content = {
                    Image(
                        painter =
                            if (selectedMode == ViewMode.GRID) painterResource(R.drawable.grid_view_active)
                            else painterResource(R.drawable.grid_view_not_active),
                        contentDescription = stringResource(R.string.grid_view_selected)
                    )
                }
            )

            ViewModeButton(
                isSelected = selectedMode == ViewMode.LIST,
                onClick = { onModeSelected(ViewMode.LIST) },
                content = {
                    Image(
                        painter =
                            if (selectedMode == ViewMode.LIST) painterResource(R.drawable.list_view_active)
                            else painterResource(R.drawable.list_view_not_active),
                        contentDescription = stringResource(R.string.list_view_active)
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
            .width(40.dp)
            .height(40.dp)
            .zIndex(if (isSelected) 1f else 0f)
            .background(backgroundColor, shape = RoundedCornerShape(Theme.radius.large))
            .then(
                if (isSelected) Modifier.border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(Theme.radius.large)
                ) else Modifier
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ViewModeTogglePreview() {
    var viewMode by remember { mutableStateOf(ViewMode.GRID) }
    CineVerseTheme {
        ViewModeToggle(
            selectedMode = viewMode,
            onModeSelected = { viewMode = it }
        )
    }
}