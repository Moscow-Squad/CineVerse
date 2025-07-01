package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.moscow.cineverse.designSystem.theme.Theme
import kotlinx.coroutines.launch

@Composable
fun ExploreTabs(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    showAllTabs: Boolean = true,
    selectedContentColor: Color = Theme.colors.brand.primary,
    unselectedContentColor: Color = Theme.colors.shade.tertiary,
    onTabSelected: (Int) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val tabsToShow = remember {
        if (showAllTabs) ExploreTabsPages.entries
        else ExploreTabsPages.entries.take(2)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabsToShow.forEachIndexed { index, currentTab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    selectedContentColor = selectedContentColor,
                    unselectedContentColor = unselectedContentColor,
                    text = { Text(text = currentTab.text) },
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExploreTabsPreview() {
    val pagerState = rememberPagerState(pageCount = { ExploreTabsPages.entries.size })
    val showAllTabs by remember { mutableStateOf(false) }

    ExploreTabs(
        pagerState = pagerState,
        showAllTabs = showAllTabs
    )
}

