package com.moscow.cineverse.designSystem.component.tabs

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun ExploreTabs(
    selectedTab: ExploreTabsPages,
    onTabSelected: (ExploreTabsPages) -> Unit,
    modifier: Modifier = Modifier,
    showAllTabs: Boolean = true,
    selectedContentColor: Color = Theme.colors.brand.primary,
    unselectedContentColor: Color = Theme.colors.shade.tertiary,
) {

    val tabsToShow = remember(showAllTabs) {
        if (showAllTabs) ExploreTabsPages.entries
        else ExploreTabsPages.entries.take(2)
    }


    TabRow(
        selectedTabIndex = tabsToShow.indexOf(selectedTab),
        contentColor = selectedContentColor,
        containerColor = Theme.colors.background.screen,
        modifier = modifier,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[tabsToShow.indexOf(selectedTab)])
                    .padding(horizontal = 16.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = Theme.radius.full,
                            topEnd = Theme.radius.full
                        )
                    )
                ,
                color = selectedContentColor,
            )
        },
        divider = {
            HorizontalDivider(
                thickness = 1.dp,
                color = Theme.colors.stroke.primary
            )
        }
    ) {
        tabsToShow.forEach { tab ->
            Tab(
                selected = selectedTab == tab,
                selectedContentColor = selectedContentColor,
                unselectedContentColor = unselectedContentColor,
                text = {
                    Text(
                        text = stringResource(tab.textId),
                        style = Theme.textStyle.body.medium.medium
                    )
                },
                onClick = { onTabSelected(tab) }
            )
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
        var selectedTab by remember { mutableStateOf(ExploreTabsPages.MOVIES) }
        ExploreTabs(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            showAllTabs = true
        )
    }
}
