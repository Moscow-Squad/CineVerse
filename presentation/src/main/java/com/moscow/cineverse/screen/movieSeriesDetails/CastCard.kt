package com.moscow.cineverse.screen.movieSeriesDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cinverse.presentation.R

@Composable
fun <T> CastCard(
    enableBlur: String,
    modifier: Modifier = Modifier,
    castMember: T,
    getOriginalName: (T) -> String,
    getCharacterName: (T) -> String,
    getProfileImage: (T) -> String,
) {
    val isPreview = LocalInspectionMode.current
    Row(
        modifier = Modifier
            .width(200.dp)
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.background.card)
            .then(modifier)
    ) {
        if (isPreview) {
            Image(
                painter = painterResource(R.drawable.profile_image),
                contentDescription = "Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(
                        RoundedCornerShape(
                            topEnd = Theme.radius.large,
                            topStart = Theme.radius.large,
                            bottomStart = Theme.radius.large
                        )
                    ),
                contentScale = ContentScale.Crop
            )
        } else {
            SafeImageViewer(
                imageUrl = getProfileImage(castMember),
                modifier = Modifier
                    .size(64.dp)
                    .clip(
                        RoundedCornerShape(
                            topEnd = Theme.radius.large,
                            topStart = Theme.radius.large,
                            bottomStart = Theme.radius.large
                        )
                    ),
                isBlurEnabled = enableBlur,
                placeholderContent = { RemoteImagePlaceholder() },
                errorContent = { RemoteImagePlaceholder() },
                onBlurContent = {
                    OnBlurContent(
                        hintText = stringResource(R.string.unsuitable_image),
                        isAddedText = false
                    )
                }
            )
        }
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = getOriginalName(castMember),
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.shade.primary,
                maxLines = 1
            )

            Text(
                text = getCharacterName(castMember),
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.secondary,
                maxLines = 1
            )
        }

    }
}
