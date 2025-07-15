package com.moscow.cineverse.designSystem.component.cast_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MainDetails(
    profileImage: String,
    name: String,
    date: String,
    location: String,
    scrollState: ScrollState?,
    socialMediaLinks: SocialMediaLinks,
    modifier: Modifier = Modifier,
    onSocialMediaClick: (platform: String, url: String) -> Unit = { _, _ -> },
) {
    val isCollapsed by remember {
        derivedStateOf {
            scrollState?.value?.let { it > 100 } ?: false
        }
    }

    val imageSize by animateDpAsState(
        targetValue = if (isCollapsed) 48.dp else 80.dp,
        animationSpec = tween(durationMillis = 300)
    )

    val cornerSize by animateDpAsState(
        targetValue = if (isCollapsed) 999.dp else 16.dp,
        animationSpec = tween(durationMillis = 300)
    )

    Card(
        shape = RoundedCornerShape(Theme.radius.extraLarge),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card),
        modifier = modifier.padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SafeImageViewer(
                    model = profileImage,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(imageSize)
                        .clip(if (isCollapsed) CircleShape else RoundedCornerShape(cornerSize))
                )
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text(
                        text = name,
                        color = Theme.colors.shade.primary,
                        style = Theme.textStyle.title.medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    AnimatedVisibility(visible = !isCollapsed) {
                        Column {
                            TextWithIcon(
                                icon = R.drawable.outline_cake,
                                text = date,
                                textColour = Theme.colors.shade.secondary,
                                iconTint = Theme.colors.shade.secondary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            TextWithIcon(
                                icon = R.drawable.outline_location,
                                text = location,
                                iconTint = Theme.colors.shade.secondary,
                                textColour = Theme.colors.shade.secondary,
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SocialMediaPill(
                    name = "YouTube",
                    iconRes = R.drawable.colored_youtube,
                    url = socialMediaLinks.youtube,
                    onClick = { onSocialMediaClick("youtube", it) },
                    modifier = Modifier.weight(1f)
                )

                SocialMediaPill(
                    name = "Facebook",
                    iconRes = R.drawable.colored_facebook,
                    url = socialMediaLinks.facebook,
                    onClick = { onSocialMediaClick("facebook", it) },
                    modifier = Modifier.weight(1f)
                )

                SocialMediaPill(
                    name = "Instagram",
                    iconRes = R.drawable.colored_instagram,
                    url = socialMediaLinks.instagram,
                    onClick = { onSocialMediaClick("instagram", it) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SocialMediaPill(
    name: String,
    iconRes: Int,
    url: String?,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (name.lowercase()) {
        "youtube" -> Theme.colors.shade.quinary to Theme.colors.shade.primary
        "facebook" -> Theme.colors.shade.quinary to Theme.colors.shade.primary
        "instagram" -> Theme.colors.shade.quinary to Theme.colors.shade.primary
        else -> Theme.colors.shade.quinary to Theme.colors.shade.primary
    }

    Box(
        modifier = modifier
            .height(32.dp)
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(backgroundColor)
            .clickable { url?.let { onClick(it) } }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "$name icon"
            )

            Text(
                text = name,
                color = textColor,
                style = Theme.textStyle.label.medium.medium
            )
        }
    }
}