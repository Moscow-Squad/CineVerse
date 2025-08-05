package com.moscow.cineverse.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun HistoryTip(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(width = 328.dp, height = 55.dp)
            .background(
                color = Theme.colors.background.card,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = Theme.colors.stroke.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                painter = painterResource(Theme.icons.dueTone.infoCircle),
                contentDescription = "Info",
                tint = Theme.colors.brand.primary,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = stringResource(R.string.history_tip_message),
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.primary,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(19.5.dp)
            ) {
                Icon(
                    painter = painterResource(Theme.icons.outline.xClose),
                    contentDescription = "Close tip",
                    tint = Theme.colors.shade.secondary,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun HistoryTipPreview() {
    CineVerseTheme {
        HistoryTip(
            onDismiss = {}
        )
    }
}