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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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

@Composable
fun CastGallery(
    images: List<String>,
    modifier: Modifier = Modifier,
) {
    val imageGroups = images.chunked(3)

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(imageGroups) { index, image ->
            CastGalleryItem(
                images = image,
                isFlipped = index % 2 == 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }
    }
}


@Composable
fun CastGalleryItem(
    images: List<String>,
    isFlipped: Boolean,
    modifier: Modifier = Modifier
) {
    val layoutModifier = if (isFlipped) {
        modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = -1f }
    } else {
        modifier.fillMaxWidth()
    }

    val imageFlipModifier = if (isFlipped) Modifier.graphicsLayer { scaleX = -1f } else Modifier

    @Composable
    fun CastGalleryItemImage(
        imageUrl: String,
        modifier: Modifier
    ) {
        SafeImageViewer(
            model = imageUrl,
            contentDescription = stringResource(R.string.cast_image),
            contentScale = ContentScale.Crop,
            modifier = modifier.then(imageFlipModifier),
            onLoading = { it.painter },
            fallback = painterResource(R.drawable.due_tone_image),
            placeholder = painterResource(R.drawable.due_tone_image)
        )
    }

    when (images.size) {
        1 -> {
            CastGalleryItemImage(
                imageUrl = images[0],
                modifier = layoutModifier
                    .height(280.dp)
                    .clip(RoundedCornerShape(Theme.radius.large))
            )
        }

        2 -> {
            Row(
                modifier = layoutModifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                images.forEach { image ->
                    CastGalleryItemImage(
                        imageUrl = image,
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
                modifier = layoutModifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CastGalleryItemImage(
                    imageUrl = images[0],
                    modifier = Modifier
                        .weight(2f)
                        .height(280.dp)
                        .clip(RoundedCornerShape(Theme.radius.large))
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    CastGalleryItemImage(
                        imageUrl = images[1],
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(134.dp)
                            .clip(RoundedCornerShape(Theme.radius.large))
                    )
                    CastGalleryItemImage(
                        imageUrl = images[2],
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

@Preview
@Composable
private fun GalleryCastImagesPreview() {
    CineVerseTheme {
        CastGallery(
            listOf(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Christian_Bale-7837.jpg/330px-Christian_Bale-7837.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Christian_Bale-7837.jpg/330px-Christian_Bale-7837.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Christian_Bale-7837.jpg/330px-Christian_Bale-7837.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Christian_Bale-7837.jpg/330px-Christian_Bale-7837.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Christian_Bale-7837.jpg/330px-Christian_Bale-7837.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Christian_Bale-7837.jpg/330px-Christian_Bale-7837.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Christian_Bale-7837.jpg/330px-Christian_Bale-7837.jpg"
            )
        )
    }
}