package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun MovieAppBar(
    modifier: Modifier = Modifier,
    backButtonClick: () -> Unit = {},
    addButtonClick: () -> Unit = {},
    textPaddings: PaddingValues = PaddingValues(0.dp),
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
            .background(Theme.colors.background.screen)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                IconButton(
                    onClick = { backButtonClick() },
                    modifier = Modifier.size(40.dp)
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
                modifier = Modifier.weight(1f).padding(textPaddings),
            ) {
                if (caption != null) {
                    Text(
                        text = caption,
                        style = Theme.textStyle.body.medium.regular,
                        color = Theme.colors.shade.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (title != null) {
                    Text(
                        text = title,
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            if (showAddButton) {
                IconButton(
                    onClick = addButtonClick,
                    modifier = Modifier.size(40.dp)
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
            caption = "Caption"
        )
    }
}