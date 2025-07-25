package com.moscow.cineverse.designSystem.component.movieSeriesDetails

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun <T> CastCard(
    modifier: Modifier = Modifier,
    castMember: T,
    getOriginalName: (T) -> String,
    getCharacterName: (T) -> String,
    getProfileImage: (T) -> String
) {
    val isPreview = LocalInspectionMode.current
    Row(
        modifier = modifier
            .width(200.dp)
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.background.card)
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
                placeholderContent = { RemoteImagePlaceholder() },
                errorContent = { RemoteImagePlaceholder() },
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

@Preview
@Composable
private fun CastCardPreview() {
    CineVerseTheme {
        Column(
            modifier = Modifier
                .background(Theme.colors.background.screen)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            /*CastCard(
                castMember = CastMember(
                    realName = "Tom Cruise",
                    nameInMovie = "Ethan Hunt",
                    imageUrl = "https://wror.com/uploads/2025/05/GettyImages-1511406162.jpg?format=auto&optimize=high&width=1440"
                )
            )
            CastCard(
                castMember = CastMember(
                    realName = "Tom Cruise",
                    nameInMovie = "Ethan Hunt",
                    imageUrl = null
                )
            )*/
        }
    }
}
