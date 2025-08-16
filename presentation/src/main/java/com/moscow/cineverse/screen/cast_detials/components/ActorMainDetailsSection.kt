package com.moscow.cineverse.screen.cast_detials.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.mapper.formatDate
import com.moscow.cineverse.screen.cast_detials.CastDetailsInteractionListener
import com.moscow.cineverse.screen.cast_detials.CastDetailsUiState
import com.moscow.cineverse.screen.cast_detials.CastDetailsUiState.SocialMediaLinks
import com.moscow.cinverse.presentation.R

@Composable
fun ActorMainDetailsSection(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    uiState.actor?.let { actor ->
        MainDetails(
            modifier = modifier,
            profileImage = actor.profileImg,
            name = actor.name,
            date = stringResource(
                R.string.born_on,
                actor.birthDate?.formatDate()?:""
            ),
            location = actor.placeOfBirth,
            scrollState = null,
            socialMediaLinks = uiState.socialMediaLinks,
            enableBlur = uiState.enableBlur,
            onSocialMediaClick = interactionListener::onSocialMediaClick,
        )
    }
}

@Composable
fun MainDetails(
    profileImage: String,
    name: String,
    date: String,
    location: String,
    scrollState: ScrollState?,
    socialMediaLinks: SocialMediaLinks,
    enableBlur: String,
    modifier: Modifier = Modifier,
    onSocialMediaClick: (platform: String, url: String) -> Unit = { _, _ -> },
) {
    val isCollapsed by remember {
        derivedStateOf {
            scrollState?.value?.let { it > 100 } == true
        }
    }

    val imageSize by animateDpAsState(
        targetValue = if (isCollapsed) 48.dp else 80.dp, animationSpec = tween(durationMillis = 300)
    )

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(Theme.radius.extraLarge))
            .fillMaxWidth()
            .background(Theme.colors.background.card),
    ) {
        Row(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 12.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SafeImageViewer(
                imageUrl = profileImage,
                modifier = Modifier
                    .width(68.dp)
                    .size(imageSize)
                    .clip(
                        if (isCollapsed) CircleShape else RoundedCornerShape(
                            topStart = Theme.radius.extraLarge,
                            topEnd = Theme.radius.small,
                            bottomStart = Theme.radius.small,
                            bottomEnd = Theme.radius.extraLarge
                        )
                    ),
                isBlurEnabled = enableBlur,
                placeholderContent = { RemoteImagePlaceholder() },
                errorContent = { RemoteImagePlaceholder() },
                onBlurContent = { OnBlurContent(isAddedText = false) }
            )
            Column(
                modifier = Modifier.padding(start = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = name,
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.title.medium,
                )

                AnimatedVisibility(visible = !isCollapsed) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextWithIcon(
                            icon = R.drawable.outline_cake,
                            text = date,
                            textColour = Theme.colors.shade.secondary,
                            iconTint = Theme.colors.shade.secondary,
                        )
                        if (location.isNotBlank())
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
        if (
            !socialMediaLinks.youtube.isNullOrBlank()   ||
            !socialMediaLinks.facebook.isNullOrBlank()  ||
            !socialMediaLinks.instagram.isNullOrBlank() ||
            !socialMediaLinks.twitter.isNullOrBlank()   ||
            !socialMediaLinks.tiktok.isNullOrBlank()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterHorizontally)
        ) {
            Spacer(modifier.width(8.dp))

            if (!socialMediaLinks.youtube.isNullOrBlank()) {
                SocialMediaPill(
                    name = stringResource(R.string.youtube),
                    iconRes = R.drawable.colored_youtube,
                    url = socialMediaLinks.youtube,
                    onClick = { onSocialMediaClick("youtube", it) },
                )
            }

            if (!socialMediaLinks.facebook.isNullOrBlank()) {
                SocialMediaPill(
                    name = stringResource(R.string.facebook),
                    iconRes = R.drawable.colored_facebook,
                    url = socialMediaLinks.facebook,
                    onClick = { onSocialMediaClick("facebook", it) },
                )
            }

            if (!socialMediaLinks.instagram.isNullOrBlank()) {
                SocialMediaPill(
                    name = stringResource(R.string.instagram),
                    iconRes = R.drawable.colored_instagram,
                    url = socialMediaLinks.instagram,
                    onClick = { onSocialMediaClick("instagram", it) },
                )
            }

            if (!socialMediaLinks.twitter.isNullOrBlank()) {
                SocialMediaPill(
                    name = stringResource(R.string.twitter),
                    iconRes = R.drawable.colored_x,
                    url = socialMediaLinks.twitter,
                    iconColor = Theme.colors.shade.primary,
                    onClick = { onSocialMediaClick("twitter", it) },
                )
            }

            if (!socialMediaLinks.tiktok.isNullOrBlank()) {
                SocialMediaPill(
                    name = stringResource(R.string.tik_tok),
                    iconRes = R.drawable.colored_tiktok,
                    url = socialMediaLinks.tiktok,
                    onClick = { onSocialMediaClick("tiktok", it) },
                )
            }
            Spacer(modifier.width(8.dp))
        }
    }
}
