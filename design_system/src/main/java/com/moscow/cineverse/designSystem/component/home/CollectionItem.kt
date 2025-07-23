package com.moscow.cineverse.designSystem.component.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.designSystem.theme.ThemeState

@Composable
fun CollectionItem(
    description: String,
    imageURL: String,
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
//                SafeImageViewer(
//                    model = imageURL,
//                    contentDescription = stringResource(R.string.collection_image),
//                    contentScale = ContentScale.FillWidth,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(80.dp),
//                    onLoading = { it.painter },
//                    fallback = painterResource(if (isSystemInDarkTheme()) R.drawable.loading_dark else R.drawable.loading_light),
//                    placeholder = painterResource(if (isSystemInDarkTheme()) R.drawable.loading_dark else R.drawable.loading_light)
//                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Theme.colors.overlay.primary.copy(alpha = 0.8f)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = description,
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
fun LoadingImage() {
    Image(
        painter = painterResource(R.drawable.due_tone_image),
        contentDescription = stringResource(R.string.collection_image),
    )
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

@Preview(showBackground = true)
@Composable
private fun CollectionItemPreview() {
    CineVerseTheme(state = ThemeState(isDark = true) {}) {
        CollectionItem(
            description = "Text",
            imageURL = "https://upload.wikimedia.org/wikipedia/commons/2/23/Real_Monasterio_de_San_Juan_de_la_Pe%C3%B1a%2C_Huesca%2C_Espa%C3%B1a%2C_2023-01-05%2C_DD_48-50_HDR.jpg",
            onClick = {}
        )
    }
}