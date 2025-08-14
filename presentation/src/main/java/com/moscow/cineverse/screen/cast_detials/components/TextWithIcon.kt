package com.moscow.cineverse.screen.cast_detials.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun TextWithIcon(
    text: String,
    @DrawableRes icon: Int,
    textColour: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier
                .size(20.dp)
                .padding(end = 4.dp)

        )
        Text(
            text = text,
            color = textColour,
            style = Theme.textStyle.label.medium.regular,
        )
    }
}

@Preview
@Composable
private fun TextWithIconPreview() {
    TextWithIcon(
        text = "Facebook",
        icon = R.drawable.colored_facebook,
        textColour = Theme.colors.shade.primary,
        iconTint = Color.Unspecified,
        modifier = Modifier.padding(8.dp)
    )
}