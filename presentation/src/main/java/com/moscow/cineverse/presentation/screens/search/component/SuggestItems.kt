package com.moscow.cineverse.presentation.screens.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moscow.cineverse.designSystem.component.search.SuggestItem
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.presentation.screens.search.SuggestItemUiState

@Composable
fun SuggestItems(
    suggestItems: List<SuggestItemUiState>,
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {
        suggestItems.forEachIndexed { index, item ->
            val title = item.title
            val icon = if (item.isHistory) Theme.icons.outline.history else Theme.icons.outline.search
            SuggestItem(title = title, icon = icon)
        }
    }
}