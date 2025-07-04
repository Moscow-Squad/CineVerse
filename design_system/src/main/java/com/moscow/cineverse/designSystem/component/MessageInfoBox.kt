package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.design_system.R
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
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = Theme.colors.brand.tertiary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(width = 15.15.dp, height = 23.33.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Theme.colors.brand.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id  = R.drawable.outline_arrow_left),
                    contentDescription = stringResource(R.string.arrow_left),
                    tint = Theme.colors.brand.primary,
                    modifier = Modifier.width(16.33.dp).height(9.56.dp)
                )
            }
        }

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
