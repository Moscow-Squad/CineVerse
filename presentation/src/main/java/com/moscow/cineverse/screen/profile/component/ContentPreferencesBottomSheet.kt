package com.moscow.cineverse.screen.profile.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R
import kotlinx.coroutines.delay

@Composable
fun ContentPreferencesBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    selectedPreference: String,
    onClickPreference: (String) -> Unit
) {
    AnimatedVisibility(visible) {
        CineVerseBottomSheet(
            title = stringResource(R.string.content_preferences),
            onClose = onDismiss,
            onDismissRequest = onDismiss,
            showCancelIcon = true,
        ) {
            ContentPreferencesContent(
                selectedPreference = selectedPreference,
            ) { newPreference ->
                onClickPreference(newPreference)
            }
        }
    }
}

@Composable
fun ContentPreferencesContent(
    modifier: Modifier = Modifier,
    selectedPreference: String,
    onPreferenceChanged: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.choose_how_you_d_like_to_handle_sensitive_or_revealing_images_in_the_app),
            color = Theme.colors.shade.secondary,
            style = Theme.textStyle.body.small.regular
        )

        PreferenceOption(
            name = stringResource(R.string.hide_explicit_content),
            description = stringResource(R.string.hides_revealing_or_inappropriate_posters_e_g_nudity_strong_sexual_content),
            imageRes = painterResource(R.drawable.icon_eye_with_slash),
            isSelected = selectedPreference == "high",
            onClick = { onPreferenceChanged("high") }
        )
        PreferenceOption(
            name = stringResource(R.string.strict_filtering),
            description = stringResource(R.string.hides_all_content_that_includes_immodest_clothing_or_behavior),
            imageRes = painterResource(R.drawable.slash),
            isSelected = selectedPreference == "medium",
            onClick = { onPreferenceChanged("medium") }
        )
        PreferenceOption(
            name = stringResource(R.string.show_all_content),
            description = stringResource(R.string.no_filtering_all_images_and_posters_will_be_displayed),
            imageRes = painterResource(R.drawable.icon_eye),
            isSelected = selectedPreference == "low",
            onClick = { onPreferenceChanged("low") }
        )
    }
}

@Composable
fun PreferenceOption(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    imageRes: Painter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.brand.secondary else Color.Transparent
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.brand.tertiary else Theme.colors.background.bottomSheetCard
    )
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.brand.primary else Theme.colors.shade.secondary
    )

    val iconBackground by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.brand.secondary else Theme.colors.shade.quaternary
    )

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }

    Row(
        modifier = modifier
            .scale(scale)
            .fillMaxWidth()
            .background(
                color = backgroundColor, shape = RoundedCornerShape(Theme.radius.large)
            )
            .border(
                width = 1.dp, color = borderColor, shape = RoundedCornerShape(Theme.radius.large)
            )
            .clickable {
                isPressed = true
                onClick()
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = iconBackground, shape = RoundedCornerShape(Theme.radius.medium)
                )
                .padding(8.dp), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = imageRes,
                contentDescription = "preference icon",
                tint = iconColor,
                modifier = Modifier.size(16.dp)

            )
        }
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                color = if (isSelected) Theme.colors.brand.primary else Theme.colors.shade.primary,
                style = Theme.textStyle.body.medium.medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                color = Theme.colors.shade.secondary,
                style = Theme.textStyle.body.medium.medium
            )
        }
    }
}
