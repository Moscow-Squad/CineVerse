package com.moscow.cineverse.component.bottomNavigationBar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun RowScope.NavBarEntry(
    isSelected: Boolean,
    currentItem: BottomNavItem,
    onItemClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .weight(1f)
            .clickable(
                enabled = !isSelected,
                onClick = {
                    if (!isSelected) {
                        onItemClick()
                    }
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            NavBarIcon(
                currentItem = currentItem,
                selected = isSelected
            )
            Text(
                text = stringResource(currentItem.label),
                style = Theme.textStyle.label.medium.regular,
                color = animateColorAsState(
                    targetValue = if (isSelected) Theme.colors.brand.primary else Theme.colors.shade.tertiary,
                    label = "NavBarItemTextColor"
                ).value,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}
