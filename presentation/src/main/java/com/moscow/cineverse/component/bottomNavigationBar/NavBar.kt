package com.moscow.cineverse.component.bottomNavigationBar

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.AppDestination

@Composable
fun NavBar(
    selectedItem: BottomNavItem,
    destinations: List<BottomNavItem> = BottomNavItem.destinations.values.toList(),
    onItemClick: (Int,AppDestination) -> Unit
) {

    NavigationBar(
        containerColor = Theme.colors.background.card,
    ) {
        destinations.forEachIndexed { index, item ->
            NavBarEntry(
                isSelected = item == selectedItem,
                currentItem = item,
                onItemClick = {
                    onItemClick(index,item.destination)
                }
            )
        }
    }
}
