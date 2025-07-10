package com.moscow.cineverse.designSystem.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun CineVerseSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val isDarkTheme = isSystemInDarkTheme()

    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = isEnabled,
        thumbContent = {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        color = when {
                            !isEnabled -> Theme.colors.shade.tertiary
                            checked -> Color.White
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

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun CineVerseSwitchPreview() {
    var enabledChecked by remember { mutableStateOf(true) }
    var enabledUnchecked by remember { mutableStateOf(false) }
    var disabledChecked by remember { mutableStateOf(true) }
    var disabledUnchecked by remember { mutableStateOf(false) }

    CineVerseTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                // Enabled states
                Text(
                    "Enabled States",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CineVerseSwitch(
                        checked = enabledChecked,
                        onCheckedChange = { enabledChecked = it },
                        isEnabled = true
                    )
                    Text(
                        "Enabled - Checked",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CineVerseSwitch(
                        checked = enabledUnchecked,
                        onCheckedChange = { enabledUnchecked = it },
                        isEnabled = true
                    )
                    Text(
                        "Enabled - Unchecked",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Text(
                    "Disabled States",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CineVerseSwitch(
                        checked = disabledChecked,
                        onCheckedChange = { },
                        isEnabled = false
                    )
                    Text(
                        "Disabled - Checked",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CineVerseSwitch(
                        checked = disabledUnchecked,
                        onCheckedChange = { },
                        isEnabled = false
                    )
                    Text(
                        "Disabled - Unchecked",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Preview(name = "Compact Layout - Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Compact Layout - Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun CineVerseSwitchCompactPreview() {
    CineVerseTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                CineVerseSwitch(
                    checked = true,
                    onCheckedChange = { },
                    isEnabled = true
                )

                CineVerseSwitch(
                    checked = false,
                    onCheckedChange = { },
                    isEnabled = true
                )

                CineVerseSwitch(
                    checked = false,
                    onCheckedChange = { },
                    isEnabled = false
                )

                CineVerseSwitch(
                    checked = true,
                    onCheckedChange = { },
                    isEnabled = false
                )
            }
        }
    }
}