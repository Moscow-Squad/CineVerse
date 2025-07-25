package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MovieAppBar(
    modifier: Modifier = Modifier,
    backButtonClick: () -> Unit = {},
    addButtonClick: () -> Unit = {},
    title: String? = null,
    caption: String? = null,
    showBackButton: Boolean = true,
    showAddButton: Boolean = false,
    showLogo: Boolean = false,
    showDivider: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Theme.colors.background.screen
            ),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                IconButton(
                    onClick = { backButtonClick() },
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_left),
                        contentDescription = "Back",
                        tint = Theme.colors.shade.primary
                    )
                }
            }

            if (showLogo) {
                Image(
                    painter = painterResource(R.drawable.colored_cineverse_logo),
                    contentDescription = ""
                )
            }
            Column(
                modifier = Modifier.weight(1f),
            ) {
                if (caption != null) {
                    Text(
                        text = caption,
                        style = Theme.textStyle.body.medium.regular,
                        color = Theme.colors.shade.secondary
                    )
                }
                if (title != null) {
                    Text(
                        text = title,
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary
                    )
                }
            }
            if (showAddButton) {
                IconButton(
                    onClick = addButtonClick,
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_add),
                        contentDescription = "Search",
                        tint = Theme.colors.shade.primary
                    )

                }
            }
        }
        if (showDivider) {
            HorizontalDivider(
                thickness = 1.dp,
                color = Theme.colors.stroke.primary,
                modifier = Modifier
            )
        }
    }
}

@CineVersePreviews
@Composable
fun MovieAppBarPreview() {
    CineVerseTheme {
        MovieAppBar(
            title = "Title",
            caption = "Caption",

            )
    }
}