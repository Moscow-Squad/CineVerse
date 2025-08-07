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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

@Composable
fun CollectionItem(
    @StringRes titleRes: Int,
    @DrawableRes imageRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(Theme.radius.small))
                    .clickable { onClick() },
                contentAlignment = Alignment.BottomCenter
            ) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    painter = painterResource(imageRes),
                    contentDescription = "collection image",
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Theme.colors.overlay.primary.copy(alpha = 0.8f)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(titleRes),
                        style = Theme.textStyle.body.small.medium,
                        color = Theme.colors.shade.primary
                    )
                }
            }
            BackLayer()
        }
    }
}

@Composable
private fun BackLayer() {
    BackgroundLayer(
        horizontalPadding = 8.dp,
        color = Theme.colors.brand.tertiary,
    )

    BackgroundLayer(
        horizontalPadding = 16.dp,
        color = Theme.colors.shade.quinary,
    )
}

@Composable
private fun BackgroundLayer(
    horizontalPadding: Dp,
    color: Color,
    layerHeight: Dp = 6.dp,
    radius: Dp = Theme.radius.small,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = horizontalPadding)
            .clip(
                RoundedCornerShape(
                    bottomStart = radius,
                    bottomEnd = radius
                )
            )
            .fillMaxWidth()
            .height(layerHeight)
            .background(color = color),
    ) {}
}
