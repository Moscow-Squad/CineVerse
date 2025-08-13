package com.moscow.cineverse.designSystem.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.component.preview.CineVersePreviews
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun MessageInfoCard(
    title: String,
    description: String,
    icon: Painter,
    showButtonsGroup: Boolean,
    firstButtonText: String,
    onClickFirstButton: () -> Unit,
    modifier: Modifier = Modifier,
    secondButtonText: String = "",
    iconColor: Color = Theme.colors.brand.primary,
    secondButtonBackground: Color = Theme.colors.button.primary,
    onClickSecondButton: () -> Unit = {},
    firstButtonLoading: Boolean = false,
    secondButtonLoading: Boolean = false,
    iconCircleBackgroundColor: Color = Theme.colors.brand.tertiary,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(size = 64.dp)
                .background(
                    color = iconCircleBackgroundColor, shape = CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(size = 28.dp),
                painter = icon,
                contentDescription = stringResource(R.string.arrow_left),
                colorFilter = ColorFilter.tint(iconColor)
            )
        }

        Text(
            text = title,
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(
                top = 16.dp, bottom = 8.dp
            )
        )
        Text(
            text = description,
            style = Theme.textStyle.body.medium.medium,
            color = Theme.colors.shade.secondary,
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp)
        ) {
            if (showButtonsGroup) {
                MovieButton(
                    buttonText = firstButtonText,
                    textColor = Theme.colors.button.onSecondary,
                    textStyle = Theme.textStyle.body.medium.medium,
                    onClick = onClickFirstButton,
                    buttonColor = Theme.colors.button.secondary,
                    modifier = Modifier
                        .weight(weight = 1f)
                        .padding(vertical = 14.dp),
                    isLoading = firstButtonLoading
                )
            }
            MovieButton(
                buttonText = secondButtonText,
                textColor = Theme.colors.button.onPrimary,
                textStyle = Theme.textStyle.body.medium.medium,
                onClick = onClickSecondButton,
                buttonColor = secondButtonBackground,
                modifier = Modifier
                    .weight(weight = 1f)
                    .padding(vertical = 14.5.dp),
                isLoading = secondButtonLoading
            )
        }
    }
}

@CineVersePreviews
@Composable
fun MessageInfoBoxExample() {
    CineVerseTheme {
        MessageInfoCard(
            title = "No Collections Yet",
            description = "Start building your personal library by saving movies or series you want to remember.",
            firstButtonText = "Collecting",
            secondButtonText = "Log In",
            onClickFirstButton = {},
            onClickSecondButton = {},
            icon = painterResource(R.drawable.due_tone_info_circle),
            showButtonsGroup = true,
            modifier = Modifier
                .padding(50.dp)
                .background(Color.Black)
        )
    }
}