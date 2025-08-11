package com.moscow.cineverse.designSystem.component.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.preview.CineVersePreviews
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MovieButton(
    buttonText: String,
    textColor: Color,
    textStyle: TextStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textPadding: PaddingValues = PaddingValues(),
    buttonColor: Color = Color.Transparent,
    cornerRadius: Dp = Theme.radius.large,
    enableAction: Boolean = true,
    isLoading: Boolean = false
) {
    val textColorAction by animateColorAsState(if (!enableAction) Theme.colors.button.onDisabled else textColor)

    val backgroundColor by animateColorAsState(
        if (buttonColor != Color.Transparent) {
            if (enableAction) buttonColor else Theme.colors.button.disabled
        } else {
            buttonColor
        }
    )

    Row(
        modifier = modifier
            .height(
                height = 48.dp
            )
            .clip(
                shape = RoundedCornerShape(cornerRadius)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .clickable(
                enabled = enableAction,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedContent(isLoading) { state ->
            if (!state) {
                Text(
                    modifier = Modifier.padding(textPadding),
                    text = buttonText,
                    color = textColorAction,
                    style = textStyle
                )
            } else {
                MovieCircularProgressBar(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp,
                    gradientColors = listOf(
                        Theme.colors.brand.primary,
                        Theme.colors.brand.tertiary
                    )
                )
            }
        }
    }
}

@CineVersePreviews
@Composable
private fun PreviewButton() {

    var isLoading by remember { mutableStateOf(true) }
    var isEnabled by remember { mutableStateOf(true) }

    CineVerseTheme {

        MovieButton(
            buttonColor = Theme.colors.button.primary,
            buttonText = "Login",
            textColor = Theme.colors.button.onPrimary,
            textStyle = Theme.textStyle.title.small,
            isLoading = isLoading,
            enableAction = isEnabled,
            onClick = {
                isEnabled = !isEnabled

            },
            modifier = Modifier
                .padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                )
        )

    }
}