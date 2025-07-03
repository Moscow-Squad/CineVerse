package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme

@Composable

fun TextWithIcon(
    icon: Int,
    text: String,
    modifier: Modifier = Modifier,
) {

    Row(modifier = modifier)
    {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Theme.colors.shade.secondary,
            modifier = Modifier
                .size(14.33.dp)
                .padding(end = 4.dp)

        )
        Text(
            text = text,
            color = Theme.colors.shade.secondary,
            style = Theme.textStyle.label.medium.regular,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TextWithIconPreview() {
    TextWithIcon(
        icon = R.drawable.outline_location,
        text = "location"
    )
}
