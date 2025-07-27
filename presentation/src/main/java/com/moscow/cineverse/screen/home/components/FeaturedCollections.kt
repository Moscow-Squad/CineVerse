package com.moscow.cineverse.screen.home.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.home.CollectionItem
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.designSystem.theme.ThemeState
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.screen.home.HomeFeaturedCollections

@Composable
fun FeaturedCollectionsSection(
    collections: List<HomeFeaturedCollections>,
    onCollectionClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.featured_collections),
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        FeaturedCollections(
            collections = collections,
            onCollectionClick = onCollectionClick
        )
    }

}

@Composable
private fun FeaturedCollections(
    onCollectionClick: () -> Unit,
    collections: List<HomeFeaturedCollections>
) {
    FlowRow(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 3
    ) {
        collections.forEach { item ->
            CollectionItem(
                modifier = Modifier.width(280.dp),
                titleRes = item.title,
                imageRes = item.image,
                onClick = onCollectionClick
            )
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0x121321)
@Composable
private fun FeaturedCollectionsSectionPreview() {
    CineVerseTheme(state = ThemeState(isDark = true) {}) {
        val collections = HomeFeaturedCollections.entries.toList()

        FeaturedCollectionsSection(
            collections = collections,
            onCollectionClick = {}
        )
    }
}