package com.moscow.cineverse.screen.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
internal fun UserInfo(
    modifier: Modifier = Modifier,
    name: String = "",
    username: String = "",
    userImage: Painter? = null,
    isGuest: Boolean,
    onClick: () -> Unit

) {
    Row(
        modifier
            .fillMaxWidth()
            .background(
                color = Theme.colors.background.card,
                shape = RoundedCornerShape(Theme.radius.large)
            )
            .padding(12.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Box(
            modifier = modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Theme.colors.shade.quinary),
            contentAlignment = Alignment.Center
        ) {
            if (userImage != null) {
                Image(
                    painter = userImage,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Default Profile Icon",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            MovieText(
                text = if(!isGuest) name else stringResource(R.string.login_or_sign_up),
                style = Theme.textStyle.body.large.medium,
                color = Theme.colors.shade.primary,
                maxLines = 1,
            )

            MovieText(
                text = if (!isGuest) "@$username" else stringResource(R.string.to_personalize_your_experience),
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary,
                maxLines = 2,
            )
        }

        Icon(
            painter = painterResource(R.drawable.outline_alt_arrow_right),
            contentDescription = "right arrow",
            tint = Theme.colors.shade.tertiary,
            modifier = Modifier.size(20.dp)

        )
    }

}

@Preview
@Composable
private fun ProfileDataPreview() {
    CineVerseTheme {
        UserInfo(
            name = "Shrouk Mohamed",
            username = "shrouk_mohamed16",
            isGuest = false,
            onClick = {}
        )
    }

}