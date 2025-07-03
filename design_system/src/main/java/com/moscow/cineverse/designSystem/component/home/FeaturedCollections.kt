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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.designSystem.theme.ThemeState

data class FeaturedItem( // ToDo: Will be Ui_State
    val description: String,
    val image: Painter,
)

@Composable
fun FeaturedCollectionsSection(
    modifier: Modifier = Modifier,
    collections: List<FeaturedItem> // ToDo: Will be Ui_State
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
        FeaturedCollections(collections = collections)
    }

}

@Composable
private fun FeaturedCollections(
    collections : List<FeaturedItem> = emptyList()
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
                image = item.image,
                onClick = {} // ToDo: Will add Action Later
            )
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0x121321)
@Composable
private fun FeaturedCollectionsSectionPreview() {
    CineVerseTheme(state = ThemeState(isDark = true, {})) {
        val collections = listOf(
            FeaturedItem(
                description = "Description",
                image = painterResource(R.drawable.collection_image),
            ),
            FeaturedItem(
                description = "Description",
                image = painterResource(R.drawable.collection_image),
            ),
        )
        FeaturedCollectionsSection(
            collections = collections
        )
    }
}