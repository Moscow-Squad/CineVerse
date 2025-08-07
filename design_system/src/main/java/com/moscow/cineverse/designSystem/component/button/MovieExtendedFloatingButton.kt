package com.moscow.cineverse.designSystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MovieExtendedFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter,
    iconColor: Color,
    buttonText: String,
    backgroundColor: Color,
    contentPadding: PaddingValues
){
    Button(
        modifier = modifier.height(48.dp),
        onClick = onClick,
        shape = RoundedCornerShape(Theme.radius.large),
        contentPadding = contentPadding,
        colors = buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.Unspecified
        )
    ) {
        Text(modifier = Modifier.padding(end = 8.dp),text = buttonText, style = Theme.textStyle.body.medium.medium,
            color = Theme.colors.button.onPrimary)
        Icon(
            modifier = Modifier.size(20.dp),
            painter = icon,
            tint = iconColor,
            contentDescription = buttonText
        )
    }
}

@Preview()
@Composable
fun MovieExtendedFloatingButtonPreview(){
    CineVerseTheme {
        MovieExtendedFloatingButton(
            onClick = {},
            icon = painterResource(Theme.icons.outline.altArrowLeft),
            buttonText = "Get Started",
            backgroundColor = Theme.colors.button.primary,
            contentPadding = PaddingValues(14.dp),
            iconColor = Theme.colors.button.secondary
        )
    }
}