@file:OptIn(ExperimentalMaterial3Api::class)

package com.moscow.cineverse.designSystem.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

data class MovieNavItem(
    val route: String,
    val title: String,
    @DrawableRes val filledIcon: Int,
    @DrawableRes val outlinedIcon: Int
)


object MovieNavItems {
    fun getDefaultItems() = listOf(
        MovieNavItem(
            route = "home",
            title = "Home",
            filledIcon = R.drawable.due_tone_home,
            outlinedIcon = R.drawable.outline_home
        ),
        MovieNavItem(
            route = "explore",
            title = "Explore",
            filledIcon = R.drawable.due_tone_search,
            outlinedIcon = R.drawable.outline_search
        ),
        MovieNavItem(
            route = "match",
            title = "Match",
            filledIcon = R.drawable.due_tone_magic_stick,
            outlinedIcon = R.drawable.outline_magic_stick
        ),
        MovieNavItem(
            route = "me",
            title = "Me",
            filledIcon = R.drawable.due_tone_user_square,
            outlinedIcon = R.drawable.outline_user_square
        )
    )
}

@Composable
fun MovieBottomNavigation(
    navItems: List<MovieNavItem>,
    currentRoute: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Theme.colors.background.card,
    selectedColor: Color = Theme.colors.brand.primary,
    unselectedColor: Color = Theme.colors.shade.tertiary
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = containerColor,
        shadowElevation = 12.dp,
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            navItems.forEach { navItem ->
                AnimatedNavItem(
                    navItem = navItem,
                    isSelected = currentRoute == navItem.route,
                    onClick = { onItemClick(navItem.route) },
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor
                )
            }
        }
    }
}

@Composable
private fun AnimatedNavItem(
    navItem: MovieNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color,
    unselectedColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) selectedColor else unselectedColor,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "iconColor"
    )
    Box(modifier = Modifier.padding(vertical = 12.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clickable(
                    onClick = onClick,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(horizontal = 16.dp)
                .scale(if (isSelected) pulseScale else 1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding( bottom = 8.dp)
            ) {
                AnimatedIcon(
                    isSelected = isSelected,
                    filledIcon = navItem.filledIcon,
                    outlinedIcon = navItem.outlinedIcon,
                    contentDescription = navItem.title,
                    tint = iconColor,
                    modifier = Modifier
                        .scale(scale)
                )
            }

            Text(
                text = navItem.title,
                style = Theme.textStyle.label.medium.semiBold,
                color = iconColor,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun AnimatedIcon(
    isSelected: Boolean,
    @DrawableRes filledIcon: Int,
    @DrawableRes outlinedIcon: Int,
    contentDescription: String?,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Crossfade(
        targetState = isSelected,
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "iconCrossfade",
        modifier = modifier
    ) { selected ->
        Icon(
            painter = painterResource(id = if (selected) filledIcon else outlinedIcon),
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true, name = "Bottom Navigation Preview")
@Composable
fun MovieBottomNavigationPreview() {
    var currentRoute by remember { mutableStateOf("match") }

    CineVerseTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Current Route: ${currentRoute.uppercase()}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Theme.colors.brand.primary
                )
            }
            MovieBottomNavigation(
                navItems = MovieNavItems.getDefaultItems(),
                currentRoute = currentRoute,
                onItemClick = { selected ->
                    currentRoute = selected
                }
            )
        }
    }
}