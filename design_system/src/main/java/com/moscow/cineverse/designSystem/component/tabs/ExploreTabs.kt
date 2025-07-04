package com.moscow.cineverse.designSystem.component.tabs

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import kotlinx.coroutines.launch

@Composable
fun ExploreTabs(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    showAllTabs: Boolean = true,
    selectedContentColor: Color = Theme.colors.brand.primary,
    unselectedContentColor: Color = Theme.colors.shade.tertiary,
) {
    val scope = rememberCoroutineScope()
    val tabsToShow = remember(showAllTabs) {
        if (showAllTabs) ExploreTabsPages.entries
        else ExploreTabsPages.entries.take(2)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            contentColor = selectedContentColor,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = selectedContentColor
                )
            },
        ) {
            tabsToShow.forEachIndexed { index, currentTab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    selectedContentColor = selectedContentColor,
                    unselectedContentColor = unselectedContentColor,
                    text = { Text(text = stringResource(currentTab.textId)) },
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

@Preview(
    name = "Tabs Dark Mode",
    uiMode = UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Tabs Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ExploreTabsPreview() {
    CineVerseTheme {
        val pagerState = rememberPagerState(pageCount = { ExploreTabsPages.entries.size })
        val showAllTabs by remember { mutableStateOf(true) }

        ExploreTabs(
            pagerState = pagerState,
            showAllTabs = showAllTabs
        )
    }
}

