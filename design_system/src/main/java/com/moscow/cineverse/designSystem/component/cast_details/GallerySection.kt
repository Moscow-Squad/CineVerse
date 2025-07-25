package com.moscow.cineverse.designSystem.component.cast_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun GallerySection(
    images: List<String>,
    isFlipped: Boolean = false,
    modifier: Modifier = Modifier
) {
    require(images.size == 3) { "TripleImageGallery requires exactly 3 images" }

    val layoutModifier = if (isFlipped) {
        modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = -1f }
    } else {
        modifier.fillMaxWidth()
    }

    val imageFlipModifier = if (isFlipped) Modifier.graphicsLayer { scaleX = -1f } else Modifier

    @Composable
    fun GalleryImage(
        imageUrl: String,
        modifier: Modifier
    ) {
        SafeImageViewer(
            imageUrl = imageUrl,
            modifier = modifier.then(imageFlipModifier),
            placeholderContent = { RemoteImagePlaceholder() },
            onBlurContent = {
                OnBlurContent(
                    icon = painterResource(R.drawable.icon_eye_slash),
                    hintText = stringResource(R.string.unsuitable_image),
                    textStyle = Theme.textStyle.body.small.regular.copy(
                        color = Color(0x99FFFFFF)
                    ),
                    iconSize = 24.dp,
                )
            }
        )
    }

    Row(
        modifier = layoutModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            GalleryImage(
                imageUrl = images[0],
                modifier = Modifier
                    .fillMaxWidth()
                    .height(134.dp)
                    .clip(RoundedCornerShape(Theme.radius.large))
            )
            GalleryImage(
                imageUrl = images[1],
                modifier = Modifier
                    .fillMaxWidth()
                    .height(134.dp)
                    .clip(RoundedCornerShape(Theme.radius.large))
            )
        }

        GalleryImage(
            imageUrl = images[2],
            modifier = Modifier
                .weight(2f)
                .height(280.dp)
                .clip(RoundedCornerShape(Theme.radius.large))
        )
    }
}

@Composable
private fun PlaceholderCard(
    modifier: Modifier = Modifier,
    cardColor: Color = Theme.colors.brand.tertiary,
    iconSize: Dp = 24.dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                cardColor,
                RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 12.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.due_tone_image),
            contentDescription = "Movie Poster",
            tint = Theme.colors.shade.secondary,
            modifier = Modifier.size(iconSize)
        )
    }
}