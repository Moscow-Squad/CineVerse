package com.moscow.cineverse.designSystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun InfoCard(
    text: String,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(
                color = Theme.colors.background.card,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = Theme.colors.stroke.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.due_tone_info_circle),
                contentDescription = "Info",
                tint = Theme.colors.brand.primary,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = text,
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.primary,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(R.drawable.outline_x),
                contentDescription = "Close tip",
                tint = Theme.colors.shade.secondary,
                modifier = Modifier
                    .size(16.dp)
                    .clip(shape = CircleShape)
                    .clickable(onClick = onDismiss)
            )
        }
    }
}