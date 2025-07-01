package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.designSystem.theme.ThemeState

@Composable
fun MovieAppBar(modifier: Modifier = Modifier,
                backButtonClick: () -> Unit = {},
                addButtonClick: () -> Unit = {}
                ) {
    Column {
        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {backButtonClick() },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_arrow_left),
                    contentDescription = "Back",
                    tint = Theme.colors.shade.primary
                )
            }
            Image(
                painter = painterResource(R.drawable.colored_cineverse_logo),
                contentDescription = ""
            )
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = stringResource(R.string.caption),
                    style = Theme.textStyle.body.medium.regular,
                    color = Theme.colors.shade.secondary
                )
                Text(
                    text = stringResource(R.string.title),
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary
                )
            }
            IconButton(
                onClick = { addButtonClick() },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_add),
                    contentDescription = "Search",
                    tint = Theme.colors.shade.primary
                )

            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Theme.colors.stroke.primary,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun MovieAppBarPreview(modifier: Modifier = Modifier) {
    CineVerseTheme(
        ThemeState(isDark = false, onThemeChanged = {})
    ) {
        MovieAppBar()
    }
}