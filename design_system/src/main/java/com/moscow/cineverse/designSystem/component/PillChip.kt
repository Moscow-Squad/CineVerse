package com.moscow.cineverse.designSystem.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun PillLabel(
    text: String,
    onClick: () -> Unit,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .height(32.dp)
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(Theme.colors.background.card)
            .then(
                if (isActive) {
                    Modifier.border(
                        width = 2.dp,
                        color = Theme.colors.brand.secondary,
                        shape = RoundedCornerShape(Theme.radius.full)
                    )
                } else Modifier
            )
            .padding(horizontal = 10.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Left dot
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Theme.colors.brand.primary)
            )

            // Label text
            Text(
                text = text,
                color = Theme.colors.brand.primary,
                style = Theme.textStyle.label.medium.medium
            )

            // Right dot
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(RoundedCornerShape(Theme.radius.full))
                    .background(Theme.colors.shade.primary)
            )
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PillLabelExample() {
    CineVerseTheme {
        Column (
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            // Inactive state
            PillLabel(
                text = "Label",
                isActive = false,
                onClick = {}
            )

            // Active state
            PillLabel(
                text = "Label",
                isActive = true,
                onClick = {}
            )
        }
    }
}