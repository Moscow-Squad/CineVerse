package com.moscow.cineverse.designSystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MovieButton(
    buttonText: String,
    textColor: Color,
    textStyle: TextStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColor: Color = Color.Transparent,
    cornerRadius: Dp = Theme.radius.large,
    enable: Boolean = true,
    isLoading: Boolean = false
) {
    val color by animateColorAsState(if (!enable) Theme.colors.button.onDisabled else textColor)
    val backgroundColor by animateColorAsState(
        if (buttonColor != Color.Transparent)
            if (enable) buttonColor else Theme.colors.button.disabled
        else
            buttonColor
    )

    Row(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(cornerRadius))
            .clickable { if (enable) onClick() }
            .then(modifier),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    )
    {

        AnimatedContent(isLoading) { state ->
            if (state == false)
                Text(
                    text = buttonText,
                    color = color,
                    style = textStyle
                )

            // TODO:should show circular progress bar when isLoading is true
        }
    }
}

@Preview
@Composable
private fun PreviewButton() {
    var isLoading by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(true) }
    CineVerseTheme {
        MovieButton(
            buttonText = "Login",
            textColor = Theme.colors.button.onPrimary,
            textStyle = Theme.textStyle.title.small,
            isLoading = isLoading,
            enable = isEnabled,
            onClick = {
                isEnabled = !isEnabled

            },
            //  buttonColor = Theme.colors.button.primary,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
        )
    }
}