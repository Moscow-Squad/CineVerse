package com.moscow.cineverse.screen.profile.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.switcher.CineVerseSwitch
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
internal fun Settings(
    modifier: Modifier = Modifier,
    isGuest: Boolean,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    appLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.background.card)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        SettingItem(
            title = stringResource(R.string.dark_mode),
            titleColor = Theme.colors.shade.primary,
            onClick = {},
            prefixIcon = {
                Icon(
                    painter = painterResource(com.moscow.cineverse.design_system.R.drawable.due_tone_moon),
                    tint = Theme.colors.brand.primary,
                    contentDescription = "dark mode icon"
                )
            },
            suffixIcon = {
                CineVerseSwitch(
                    modifier = Modifier.size(
                        width = 40.dp,
                        height = 20.dp
                    ),
                    isDarkTheme = isDarkTheme,
                    onThemeChange = onThemeChange
                )
            })

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Theme.colors.stroke.primary)
        )
        SettingItem(
            title = stringResource(R.string.language),
            titleColor = Theme.colors.shade.primary,
            onClick = {
                var language = if(appLanguage == "ar") "en" else "ar"
                onLanguageChange(language)
                Log.d("language", "$language -- $appLanguage")
            },
            prefixIcon = {
                Icon(
                    painter = painterResource(com.moscow.cineverse.design_system.R.drawable.due_tone_language),
                    tint = Theme.colors.brand.primary,
                    contentDescription = "language icon"
                )
            },
            suffixIcon = {
                Icon(
                    painter = painterResource(com.moscow.cineverse.design_system.R.drawable.outline_alt_arrow_right),
                    tint = Theme.colors.shade.tertiary,
                    contentDescription = "language arrow icon"
                )
            })
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Theme.colors.stroke.primary)
        )
        if (!isGuest) SettingItem(
            title = stringResource(R.string.logout),
            titleColor = Theme.colors.additional.primary.red,
            onClick = {},
            prefixIcon = {
                Icon(
                    painter = painterResource(com.moscow.cineverse.design_system.R.drawable.due_tone_logout),
                    tint = Theme.colors.additional.primary.red,
                    contentDescription = "logout icon"
                )
            },
            suffixIcon = {
                Icon(
                    painter = painterResource(com.moscow.cineverse.design_system.R.drawable.outline_alt_arrow_right),
                    tint = Theme.colors.additional.primary.red,
                    contentDescription = "logout arrow icon"
                )
            })
    }

}

@Composable
internal fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color,
    onClick: () -> Unit,
    prefixIcon: @Composable () -> Unit = {},
    suffixIcon: @Composable () -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        prefixIcon()

        MovieText(
            modifier = Modifier.weight(1f),
            text = title,
            color = titleColor,
            style = Theme.textStyle.body.medium.medium
        )

        suffixIcon()

    }

}
