package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MovieButton(
    modifier: Modifier = Modifier,
    text: String ,
    onClick: () -> Unit,
    backgroundColor: Color = Theme.colors.button.primary,
    contentColor: Color = Theme.colors.button.onPrimary,
    disabledContainerColor: Color = Theme.colors.button.disabled,
    disabledContentColor: Color = Theme.colors.button.onSecondary,
    shape: Shape = RoundedCornerShape(16.dp),
    enabled: Boolean = true,
) {

    Button(

        onClick = onClick,
        modifier = modifier
            .padding(8.dp),
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor =disabledContainerColor ,
            disabledContentColor = disabledContentColor
        ),
        shape = shape,
        enabled = enabled,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = text,
                color = Theme.colors.shade.primary,
                style = Theme.textStyle.label.medium.medium
            )
        }
    }
}

@Preview
@Composable
fun MovieButtonPreview() {
    CineVerseTheme  {
        MovieButton(
            enabled = true,
            text = "Watch Movie",
            onClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}