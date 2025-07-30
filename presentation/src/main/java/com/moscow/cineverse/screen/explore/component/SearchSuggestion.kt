package com.moscow.cineverse.screen.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.SectionTitle
import com.moscow.cineverse.designSystem.component.search.SuggestItem
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.explore.SuggestItemUiState
import com.moscow.cinverse.presentation.R

@Composable
fun SearchSuggestion(
    suggestionList: List<SuggestItemUiState>,
    isHistory: Boolean,
    onClearAllClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onClickSuggestion: (SuggestItemUiState) -> Unit = {},
) {
    val title = if (isHistory)
        stringResource(R.string.history)
    else
        stringResource(R.string.search_suggestions)
    val actionTitle = if (isHistory && suggestionList.isNotEmpty())
        stringResource(R.string.clear_all_history)
    else
        null

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 104.dp)
            .background(color = Theme.colors.background.screen)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SectionTitle(
            modifier = Modifier.padding(top = 24.dp),
            title = title,
            actionTitle = actionTitle,
            onClick = onClearAllClicked
        )
        SuggestItems(suggestItems = suggestionList, onClickSuggestion = onClickSuggestion)
    }
}

@Composable
private fun SuggestItems(
    suggestItems: List<SuggestItemUiState>,
    onClickSuggestion: (SuggestItemUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Column(modifier = modifier) {
        suggestItems.forEachIndexed { index, item ->
            val title = item.title
            val icon =
                if (item.isHistory) Theme.icons.outline.history else Theme.icons.outline.search
            SuggestItem(
                title = title,
                icon = icon,
                modifier = Modifier.clickable(onClick = {
                    focusManager.clearFocus()
                    onClickSuggestion(item)
                }),
            )
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