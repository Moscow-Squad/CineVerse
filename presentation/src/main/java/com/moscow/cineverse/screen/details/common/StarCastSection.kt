package com.moscow.cineverse.screen.details.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.common_ui_state.CastUiState
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun StarCastSection(
    title: String,
    cast: List<CastUiState>,
    castContent: @Composable (CastUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(cast.chunked(2)) { rowItems ->
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    rowItems.forEach { item ->
                        castContent(item)
                    }
                }
            }
        }
    }
}
