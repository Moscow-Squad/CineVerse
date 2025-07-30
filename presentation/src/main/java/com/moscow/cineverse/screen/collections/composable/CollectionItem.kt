package com.moscow.cineverse.screen.collections.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.moscow.cineverse.designSystem.theme.ThemeState
import com.moscow.cineverse.design_system.R

@Composable
fun CollectionItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Theme.colors.background.bottomSheetCard,
                shape = RoundedCornerShape(Theme.radius.large)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Theme.icons.dueTone.folder),
            contentDescription = stringResource(R.string.collection_item_icon),
            tint = Theme.colors.brand.primary,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(20.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.shade.primary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = description,
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.secondary
            )
        }
        Icon(
            painter = painterResource(Theme.icons.outline.altArrowRight),
            contentDescription = stringResource(R.string.alt_arrow_right_icon),
            tint = Theme.colors.shade.tertiary,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(20.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewCollectionItem() {
    CineVerseTheme(
        state = ThemeState(isDark = true, onThemeChanged = {})
    ) {
        CollectionItem(
            title = "My Collection",
            description = "A collection of my favorite movies"
        )
    }
}