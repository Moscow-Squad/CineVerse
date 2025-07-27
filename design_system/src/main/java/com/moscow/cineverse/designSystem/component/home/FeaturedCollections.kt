package com.moscow.cineverse.designSystem.component.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.designSystem.theme.ThemeState
import com.moscow.cineverse.presentation.screen.home.HomeFeaturedCollections


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
            color = Theme.colors.shade.primary
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
    collections: List<FeaturedItem>
) {
    FlowRow(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 5
    ) {
        collections.forEach { item ->
            CollectionItem(
                modifier = Modifier.width(280.dp),
                description = item.description,
                imageURL = item.imageURL,
                onClick = onCollectionClick
            )
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0x121321)
@Composable
private fun FeaturedCollectionsSectionPreview() {
    CineVerseTheme(state = ThemeState(isDark = true) {}) {
        val collections = listOf(
            FeaturedItem(
                description = "Description",
                imageURL = "https://upload.wikimedia.org/wikipedia/commons/2/23/Real_Monasterio_de_San_Juan_de_la_Pe%C3%B1a%2C_Huesca%2C_Espa%C3%B1a%2C_2023-01-05%2C_DD_48-50_HDR.jpg",
            ),
            FeaturedItem(
                description = "Description",
                imageURL = "https://upload.wikimedia.org/wikipedia/commons/2/23/Real_Monasterio_de_San_Juan_de_la_Pe%C3%B1a%2C_Huesca%2C_Espa%C3%B1a%2C_2023-01-05%2C_DD_48-50_HDR.jpg",
            ),
        )
        FeaturedCollectionsSection(
            collections = collections,
            onCollectionClick = {}
        )
    }
}