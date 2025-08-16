package com.moscow.cineverse.screen.home.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.screen.home.HomeFeaturedCollections

@Composable
fun FeaturedCollectionsSection(
    collections: List<HomeFeaturedCollections>,
    onCollectionClick: (genreId: Int) -> Unit,
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
    onCollectionClick: (genreId: Int) -> Unit,
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
                onClick = { onCollectionClick(item.genreId) }
            )
        }
    }
}

