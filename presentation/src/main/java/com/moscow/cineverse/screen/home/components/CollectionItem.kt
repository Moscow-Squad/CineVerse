package com.moscow.cineverse.screen.home.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(color = Theme.colors.overlay.secondary)
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
