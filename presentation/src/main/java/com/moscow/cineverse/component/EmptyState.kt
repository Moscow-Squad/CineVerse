package com.moscow.cineverse.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun EmptyState(
    icon: Painter,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    buttonTitle: String? = null,
    showButton: Boolean = false,
    onButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(240.dp)
            .background(Theme.colors.background.screen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(
                        color = Theme.colors.brand.tertiary,
                        shape = CircleShape
                    )
            )

            Icon(
                painter = icon,
                contentDescription = "",
                tint = Theme.colors.brand.primary,
                modifier = Modifier.size(30.dp)
            )
        }

        Text(
            text = title,
            modifier = Modifier.padding(bottom = 8.dp),
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary
        )

        Text(
            text = description,
            textAlign = TextAlign.Center,
            style = Theme.textStyle.body.small.medium,
            color = Theme.colors.shade.secondary
        )

        if (showButton && buttonTitle != null) {
            MovieButton(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .width(240.dp)
                    .height(40.dp),
                buttonText = buttonTitle,
                textColor = Theme.colors.button.onPrimary,
                buttonColor = Theme.colors.button.primary,
                textStyle = Theme.textStyle.body.small.medium.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                ),
                onClick = onButtonClick
            )
        }
    }
}
