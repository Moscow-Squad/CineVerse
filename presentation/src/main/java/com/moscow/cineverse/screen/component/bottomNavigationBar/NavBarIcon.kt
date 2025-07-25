package com.moscow.cineverse.screen.component.bottomNavigationBar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun NavBarIcon(
    currentItem: BottomNavItem,
    selected: Boolean = false
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        val animatedTint by animateColorAsState(
            targetValue = if (selected) Theme.colors.brand.primary else Theme.colors.shade.tertiary,
            label = "NavBarIconTint"
        )
        Icon(
            imageVector = ImageVector.vectorResource(if (selected)currentItem.selectedIcon else currentItem.unselectedIcon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = animatedTint
        )
    }
}


