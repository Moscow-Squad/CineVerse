package com.moscow.cineverse.designSystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MainDetails(
    profileImage: String,
    name: String,
    date: String,
    location: String,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
) {
    val isCollapsed by remember {
        derivedStateOf { scrollState.value > 100 }
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
                AsyncImage(
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

            AnimatedVisibility(visible = !isCollapsed) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PillLabel(
                        text = "YouTube",
                        onClick = {},
                        isActive = true,
                        modifier = Modifier.weight(1f),
                        prefixIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.colored_youtube),
                                contentDescription = stringResource(R.string.youtube_icon)
                            )
                        }
                    )
                    PillLabel(
                        text = "Facebook",
                        onClick = {},
                        isActive = true,
                        modifier = Modifier.weight(1f),
                        prefixIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.colored_facebook),
                                contentDescription = stringResource(R.string.facebook_icon)
                            )
                        }
                    )
                    PillLabel(
                        text = "Instagram",
                        onClick = {},
                        isActive = true,
                        modifier = Modifier.weight(1f),
                        prefixIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.colored_instagram),
                                contentDescription = stringResource(R.string.instagram_icon)
                            )
                        }
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainDetailsPreview() {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        MainDetails(
            profileImage = "https://image.lexica.art/full_jpg/7515495b-982d-44d2-9931-5a8bbbf27532"          ,
            name = "Christian Bale",
            date = "Born on Jan 30, 1974",
            location = "In Cardiff, Wales, UK",
            scrollState = scrollState
        )
        Spacer(modifier = Modifier.height(1000.dp))
    }
}
