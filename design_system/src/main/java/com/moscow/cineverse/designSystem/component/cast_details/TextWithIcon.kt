package com.moscow.cineverse.designSystem.component.cast_details

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
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable

fun TextWithIcon(
    text: String,
    @DrawableRes icon: Int,
    textColour: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
) {

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically)
    {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TextWithIconPreview() {
    CineVerseTheme {
        TextWithIcon(
            icon = R.drawable.outline_location,
            text = "location",
            iconTint = Theme.colors.shade.secondary,
            textColour = Theme.colors.shade.secondary
        )
    }
}
