package com.moscow.cineverse.screen.component.bottomNavigationBar

import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.navigation.routes.ExploreRoute
import com.moscow.cineverse.navigation.routes.MatchRoute
import com.moscow.cineverse.navigation.routes.ProfileRoute
import com.moscow.cineverse.navigation.routes.HomeRoute
import com.moscow.cinverse.presentation.R

sealed class BottomNavItem(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val label: Int,
    val destination: AppDestination
) {
    data object Home : BottomNavItem(
        selectedIcon = com.moscow.cineverse.design_system.R.drawable.due_tone_home,
        unselectedIcon = com.moscow.cineverse.design_system.R.drawable.outline_home,
        label = R.string.home ,
        destination = HomeRoute
    )

    data object Explore : BottomNavItem(
        selectedIcon = com.moscow.cineverse.design_system.R.drawable.due_tone_search,
        unselectedIcon = com.moscow.cineverse.design_system.R.drawable.outline_search,
        label = R.string.explore ,
        destination = ExploreRoute
    )

    data object Match : BottomNavItem(
        selectedIcon = com.moscow.cineverse.design_system.R.drawable.due_tone_magic_stick,
        unselectedIcon = com.moscow.cineverse.design_system.R.drawable.outline_magic_stick,
        label = R.string.match,
        destination = MatchRoute
    )

    data object Me : BottomNavItem(
        selectedIcon = com.moscow.cineverse.design_system.R.drawable.due_tone_user_square,
        unselectedIcon = com.moscow.cineverse.design_system.R.drawable.outline_user_square,
        label = R.string.me,
        destination =  ProfileRoute
    )

    companion object {
        val destinations = listOf(Home, Explore, Match, Me)
    }

}