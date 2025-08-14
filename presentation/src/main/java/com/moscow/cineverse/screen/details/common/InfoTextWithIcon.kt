package com.moscow.cineverse.screen.details.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun InfoTextWithIcon(icon: Int, text: String, tint: Color) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = tint
        )
        Text(
            text = text,
            style = Theme.textStyle.label.medium.regular,
            color = Theme.colors.shade.secondary
        )
    }
}