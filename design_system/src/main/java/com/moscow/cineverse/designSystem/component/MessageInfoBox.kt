package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MessageInfoBox(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        //Image component
        Text(
            text = title,
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = description,
            style = Theme.textStyle.body.medium.medium,
            color = Theme.colors.shade.secondary,
            modifier = Modifier.padding(bottom = 24.dp),
        )
        Row {
            // Button component
            // Button component
        }
    }
}
