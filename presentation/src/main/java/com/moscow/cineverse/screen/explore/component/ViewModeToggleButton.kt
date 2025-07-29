package com.moscow.cineverse.screen.explore.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun ViewModeToggleButton(
    selectedMode: ViewMode,
    onModeSelected: (ViewMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val indicatorOffsetX by animateDpAsState(
        targetValue = when (selectedMode) {
            ViewMode.GRID -> 0.dp
            ViewMode.LIST -> 40.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
    )

    Box(
        modifier = modifier
            .width(80.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.background.card)
            .border(
                width = 1.dp,
                color = Theme.colors.stroke.primary,
                shape = RoundedCornerShape(Theme.radius.large)
            )
    ) {
        Box(
            modifier = Modifier
                .offset(x = indicatorOffsetX)
                .width(40.dp)
                .height(38.dp)
                .clip(RoundedCornerShape(Theme.radius.large))
                .background(Theme.colors.brand.tertiary)
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = { onModeSelected(ViewMode.GRID) }
            ) {
                Image(
                    painter = painterResource(
                        if (selectedMode == ViewMode.GRID) R.drawable.grid_view_active
                        else R.drawable.grid_view_not_active
                    ),
                    contentDescription = stringResource(R.string.grid_view_selected)
                )
            }

            IconButton(
                modifier = Modifier.weight(1f),
                onClick = { onModeSelected(ViewMode.LIST) }
            ) {
                Image(
                    painter = painterResource(
                        if (selectedMode == ViewMode.LIST) R.drawable.list_view_active
                        else R.drawable.list_view_not_active
                    ),
                    contentDescription = stringResource(R.string.list_view_active)
                )
            }
        }
    }
}
