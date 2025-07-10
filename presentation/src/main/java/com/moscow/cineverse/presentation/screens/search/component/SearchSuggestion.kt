package com.moscow.cineverse.presentation.screens.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.SectionTitle
import com.moscow.cineverse.presentation.screens.search.SuggestItemUiState
import com.moscow.cinverse.presentation.R

@Composable
fun SearchSuggestion(
    suggestionList: List<SuggestItemUiState>,
){
    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        SectionTitle(
            modifier = Modifier.padding(top = 24.dp),
            title = stringResource(R.string.history),
            actionTitle = stringResource(R.string.clear_all_history),
            onClick = {}
        )
        SuggestItems(suggestItems = suggestionList)
    }
}