package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.designSystem.theme.ThemeState

@Composable
fun CtaCard(
    icon: Painter,
    title: String,
    caption: String,
    onClickArrow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.background.card),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp, top = 16.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(Theme.colors.shade.quinary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = "Icon",
                tint = Theme.colors.brand.primary,
            )
        }

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
                text = caption,
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary
            )
        }

        IconButton(onClick = onClickArrow) {
            Icon(
                painter = painterResource(R.drawable.outline_alt_arrow_right),
                contentDescription = "Arrow Icon",
                tint = Theme.colors.shade.tertiary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CtaCardDarkMode() {
    CineVerseTheme(state = ThemeState(isDark = true, {})) {
        CtaCard(
            icon = painterResource(R.drawable.due_tone_magic_stick),
            title = "Title",
            caption = "Caption",
            onClickArrow = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CtaCardLightMode() {
    CineVerseTheme(state = ThemeState(isDark = false, {})) {
        CtaCard(
            icon = painterResource(R.drawable.due_tone_magic_stick),
            title = "Let Us Choose For You!",
            caption = "We’ll help you skip the scroll and go straight to the good stuff.",
            onClickArrow = {}
        )
    }
}