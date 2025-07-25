package com.moscow.cineverse.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun SuggestionWithHeader(
    header: String,
    title: String,
    message: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = header,
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(color = Theme.colors.background.card)
                .clickable(onClick = onClick)
                .padding(12.dp)
            ,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.magic_stick_icon),
                contentDescription = null,
                modifier = Modifier
                    .background(color = Theme.colors.shade.quinary, CircleShape)
                    .padding(12.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary
                )
                Text(
                    text = message,
                    style = Theme.textStyle.body.small.medium,
                    color = Theme.colors.shade.secondary
                )
            }

            Icon(
                painter = painterResource(R.drawable.outline_alt_arrow_right),
                contentDescription = null,
                tint = Theme.colors.shade.tertiary,
            )
        }
    }


}