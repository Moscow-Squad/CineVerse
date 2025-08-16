package com.moscow.cineverse.designSystem.component.app_bar

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
import com.moscow.cineverse.designSystem.component.preview.CineVersePreviews
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
            .background(color = Theme.colors.background.screen)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 56.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (showBackButton) {
                IconButton(
                    onClick = { backButtonClick() },
                    modifier = Modifier.size(size = 40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_arrow_left),
                        contentDescription = null,
                        tint = Theme.colors.shade.primary
                    )
                }
            }

            if (showLogo) {
                Image(
                    painter = painterResource(R.drawable.colored_cineverse_logo),
                    contentDescription = null
                )
            }

            Column(
                modifier = Modifier.weight(1f).padding(textPaddings)
            ) {
                caption?.let {
                    Text(
                        text = it,
                        style = Theme.textStyle.body.medium.regular,
                        color = Theme.colors.shade.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                title?.let {
                    Text(
                        text = it,
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (showAddButton) {
                IconButton(
                    onClick = addButtonClick,
                    modifier = Modifier.size(size = 40.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_add),
                        contentDescription = null,
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