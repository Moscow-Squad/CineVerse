package com.moscow.cineverse.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.common_ui_state.MyCollectionUiState
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun MyCollectionCard(
    state: MyCollectionUiState,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(color = Theme.colors.background.card)
            .padding(12.dp)
            .clickable {
                onClick(state.id)
            },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.folder_icon),
            contentDescription = null,
            modifier = Modifier
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = state.title,
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary
            )
            Text(
                text = state.numberOfShows.toString() + " " + stringResource(R.string.shows),
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary
            )
        }

        Icon(
            painter = painterResource(R.drawable.outline_alt_arrow_right),
            contentDescription = null,
            tint = Theme.colors.shade.tertiary,
        )
    }
}