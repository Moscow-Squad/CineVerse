package com.moscow.cineverse.screen.cast_details.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer

@Composable
fun ActorGallery(
    images: List<String>,
    enableBlur: String,
    modifier: Modifier = Modifier
) {
    val imageGroups = images.chunked(3)

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(imageGroups) { index, group ->
            ActorGalleryItem(
                images = group,
                isFlipped = index % 2 == 0,
                enableBlur = enableBlur,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }
    }
}

@Composable
fun ActorGalleryItem(
    images: List<String>,
    isFlipped: Boolean,
    enableBlur: String,
    modifier: Modifier = Modifier
) {
    val flipModifier = if (isFlipped) Modifier.graphicsLayer { scaleX = -1f } else Modifier
    val containerModifier = modifier
        .fillMaxWidth()
        .then(flipModifier)

    when (images.size) {
        1 -> {
            CastGalleryItemImage(
                imageUrl = images[0],
                enableBlur = enableBlur,
                modifier = containerModifier
                    .height(280.dp)
                    .clip(RoundedCornerShape(Theme.radius.large))
            )
        }

        2 -> {
            Row(
                modifier = containerModifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                images.forEach { image ->
                    CastGalleryItemImage(
                        imageUrl = image,
                        enableBlur = enableBlur,
                        modifier = Modifier
                            .weight(1f)
                            .height(280.dp)
                            .clip(RoundedCornerShape(Theme.radius.large))
                    )
                }
            }
        }

        3 -> {
            Row(
                modifier = containerModifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CastGalleryItemImage(
                    imageUrl = images[0],
                    enableBlur = enableBlur,
                    modifier = Modifier
                        .weight(2f)
                        .height(280.dp)
                        .clip(RoundedCornerShape(Theme.radius.large))
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    images.subList(1, 3).forEach { image ->
                        CastGalleryItemImage(
                            imageUrl = image,
                            enableBlur = enableBlur,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(134.dp)
                                .clip(RoundedCornerShape(Theme.radius.large))
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CastGalleryItemImage(
    imageUrl: String,
    enableBlur: String,
    modifier: Modifier = Modifier
) {
    SafeImageViewer(
        imageUrl = imageUrl,
        modifier = modifier,
        isBlurEnabled = enableBlur,
        placeholderContent = {
            RemoteImagePlaceholder()
        },
        errorContent = {
            RemoteImagePlaceholder()
        },
        onBlurContent = {
            OnBlurContent()
        }
    )
}
