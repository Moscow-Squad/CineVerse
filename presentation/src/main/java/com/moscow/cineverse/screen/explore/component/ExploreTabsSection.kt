package com.moscow.cineverse.screen.explore.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabs
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages

@Composable
fun ExploreTabsSection(
    selectedTab: ExploreTabsPages,
    onTabSelected: (ExploreTabsPages) -> Unit,
    showAllTabs: Boolean,
    modifier: Modifier = Modifier
) {
    ExploreTabs(
        selectedTab = selectedTab,
        onTabSelected = onTabSelected,
        showAllTabs = showAllTabs,
        modifier = modifier.fillMaxWidth()
    )
}
