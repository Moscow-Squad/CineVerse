package com.moscow.cineverse.presentation.screens.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.SectionTitle
import com.moscow.cineverse.designSystem.component.search.SuggestItem
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.presentation.screens.search.SuggestItemUiState
import com.moscow.cinverse.presentation.R

@Composable
fun SearchSuggestion(
    suggestionList: List<SuggestItemUiState>,
    isHistory: Boolean,
    onClearAllClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val title = if (isHistory)
        stringResource(R.string.history)
    else
        stringResource(R.string.history)

    val actionTitle = if (isHistory)
        stringResource(R.string.clear_all_history)
    else
        null

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SectionTitle(
            modifier = Modifier.padding(top = 24.dp),
            title = title,
            actionTitle = actionTitle,
            onClick = onClearAllClicked
        )
        SuggestItems(suggestItems = suggestionList)
    }
}

@Composable
private fun SuggestItems(
    suggestItems: List<SuggestItemUiState>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        suggestItems.forEachIndexed { index, item ->
            val title = item.title
            val icon =
                if (item.isHistory) Theme.icons.outline.history else Theme.icons.outline.search
            SuggestItem(title = title, icon = icon)
        }
    }
}

@Preview
@Composable
private fun SearchSuggestionPreview() {
    SearchSuggestion(
        listOf(
            SuggestItemUiState("sug1", true),
            SuggestItemUiState("sug1", true),
            SuggestItemUiState("sug1", false),
            SuggestItemUiState("sug1", false),
            SuggestItemUiState("sug1", false),
            SuggestItemUiState("sug1", false),
            SuggestItemUiState("sug1", false),
        ),
        isHistory = true,
        onClearAllClicked = {}
    )
}