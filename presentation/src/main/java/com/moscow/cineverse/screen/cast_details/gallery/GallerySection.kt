package com.moscow.cineverse.screen.cast_details.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.image_viewer.component.SafeImageViewer

@Composable
fun GallerySection(
    images: List<String>,
    modifier: Modifier = Modifier,
    isFlipped: Boolean = false
) {
    require(images.size == 3) { "GallerySection requires exactly 3 images" }

    val flipModifier = if (isFlipped) Modifier.graphicsLayer { scaleX = -1f } else Modifier
    val containerModifier = modifier.fillMaxWidth().then(flipModifier)

    Row(
        modifier = containerModifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            images.take(2).forEach { image ->
                GalleryImage(
                    imageUrl = image,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(134.dp)
                        .clip(RoundedCornerShape(Theme.radius.large))
                )
            }
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
fun GalleryImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    SafeImageViewer(
        imageUrl = imageUrl,
        modifier = modifier,
        placeholderContent = { RemoteImagePlaceholder() },
        onBlurContent = {
            OnBlurContent(
                icon = painterResource(R.drawable.icon_eye_slash),
                hintText = stringResource(R.string.unsuitable_image),
                iconSize = 24.dp,
                textStyle = Theme.textStyle.body.small.regular.copy(color = Color(0x99FFFFFF))
            )
        }
    )
}
