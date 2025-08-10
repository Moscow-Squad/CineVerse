package com.moscow.cineverse.designSystem.component.switcher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun CineVerseSwitch(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var isChecked by rememberSaveable { mutableStateOf(isDarkTheme) }

    LaunchedEffect(isDarkTheme) {
        if (isChecked != isDarkTheme) {
            isChecked = isDarkTheme
        }
    }

    Switch(
        checked = isChecked,
        onCheckedChange = { checked ->
            isChecked = checked
            onThemeChange(checked)
        },
        enabled = true,
        thumbContent = {
            Box(
                modifier = Modifier
                    .size(size = 18.dp)
                    .clip(shape = CircleShape)
                    .background(
                        color = when {
                            isChecked -> Color.White
                            isDarkTheme -> Theme.colors.shade.secondary
                            else -> Theme.colors.shade.tertiary
                        }
                    )
            )
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = Theme.colors.brand.primary,
            checkedBorderColor = Theme.colors.brand.primary,

            uncheckedThumbColor = if (isDarkTheme) {
                Theme.colors.shade.secondary
            } else {
                Theme.colors.shade.tertiary
            },

            uncheckedTrackColor = Theme.colors.shade.quaternary,
            uncheckedBorderColor = Theme.colors.shade.secondary,

            disabledCheckedThumbColor = Theme.colors.shade.tertiary,
            disabledCheckedTrackColor = Theme.colors.shade.quaternary,
            disabledCheckedBorderColor = Theme.colors.shade.tertiary,

            disabledUncheckedThumbColor = Theme.colors.shade.tertiary,
            disabledUncheckedTrackColor = Theme.colors.shade.quaternary,
            disabledUncheckedBorderColor = Theme.colors.shade.tertiary
        ),
        modifier = modifier
    )
}