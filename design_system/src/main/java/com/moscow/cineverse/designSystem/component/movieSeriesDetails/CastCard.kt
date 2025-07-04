package com.moscow.cineverse.designSystem.component.movieSeriesDetails

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun CastCard(
    modifier: Modifier = Modifier,
    castMember: CastMember
) {
    Row(
        modifier = modifier
            .width(200.dp)
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.background.card)
    ) {
        AsyncImage(
            model = castMember.imageUrl,
            contentDescription = stringResource(R.string.cast_member_image),
            placeholder = painterResource(R.drawable.due_tone_profile),
            fallback = painterResource(R.drawable.due_tone_profile),
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

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = castMember.realName,
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.shade.primary,
                maxLines = 2
            )

            Text(
                text = castMember.nameInMovie,
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.secondary,
                maxLines = 2
            )
        }

    }
}

@Preview
@Composable
private fun CastCardPreview() {
    CineVerseTheme {
        Column (
            modifier = Modifier
                .background(Theme.colors.background.screen)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            CastCard(
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
            )
        }
    }
}
