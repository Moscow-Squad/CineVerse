package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.designSystem.theme.ThemeState

@Composable
fun CircleImage(
    isLoading: Boolean,
    hasImage: Boolean,
    modifier: Modifier = Modifier,
    image: Painter = painterResource(R.drawable.profile_image),
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Theme.colors.background.card)
            .applyIf { !hasImage },
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                Image(
                    painter = painterResource(R.drawable.due_tone_image),
                    contentDescription = "Loading profile image",
                    colorFilter = ColorFilter.tint(Theme.colors.brand.secondary),
                )
            }

            hasImage -> {
                Image(
                    painter = image,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                Image(
                    painter = painterResource(R.drawable.due_tone_profile),
                    contentDescription = "Default profile avatar",
                    colorFilter = ColorFilter.tint(Theme.colors.shade.secondary)
                )
            }
        }
    }
}

@Composable
private fun Modifier.applyIf(noImage: () -> Boolean): Modifier {
    return if (noImage()) this.circularBorder() else this
}


@Composable
private fun Modifier.circularBorder(): Modifier {
    return this.border(
        width = 1.dp,
        color = Theme.colors.stroke.primary,
        shape = CircleShape
    )
}

@Preview(showBackground = true, backgroundColor = 0x1B1C2A)
@Composable
private fun CircleImageDarkMode() {
    CineVerseTheme(
        state = ThemeState(isDark = true, {})
    ) {
        Column {
            CircleImage(hasImage = true, isLoading = false, image = painterResource(R.drawable.profile_image))
            Spacer(Modifier.height(10.dp))
            CircleImage(hasImage = false, isLoading = true)
            Spacer(Modifier.height(10.dp))
            CircleImage(hasImage = false, isLoading = false)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CircleImageLightMode() {
    CineVerseTheme(
        state = ThemeState(isDark = false, {})
    ) {
        Column {
            CircleImage(hasImage = true, isLoading = false, image = painterResource(R.drawable.profile_image))
            Spacer(Modifier.height(10.dp))
            CircleImage(hasImage = false, isLoading = true)
            Spacer(Modifier.height(10.dp))
            CircleImage(hasImage = false, isLoading = false)
        }
    }
}