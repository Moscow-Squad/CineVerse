package com.moscow.cineverse.designSystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.preview.CineVersePreviews
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun InfoCard(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = Theme.colors.background.card,
    onDismiss: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(containerColor)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.due_tone_info_circle),
            contentDescription = null,
            modifier = Modifier.padding(
                top = 17.5.dp,
                bottom = 17.5.dp,
                start = 12.dp,
                end = 8.dp,
            ),
            tint = Theme.colors.brand.primary
        )
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(weight = 1f)
                .padding(
                    top = 19.5.dp,
                    bottom = 19.5.dp,
                    end = 8.dp,
                )
        ) {
            Text(
                text = text,
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.primary,
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.outline_x),
            contentDescription = null,
            modifier = Modifier
                .clip(shape = CircleShape)
                .clickable(onClick = onDismiss)
                .padding(
                    top = 19.5.dp, bottom = 19.5.dp, end = 8.dp
                ),
            tint = Theme.colors.shade.secondary
        )
    }
}


@CineVersePreviews
@Composable
fun InfoCardPreview() {
    InfoCard(
        text = "text"
    )
}