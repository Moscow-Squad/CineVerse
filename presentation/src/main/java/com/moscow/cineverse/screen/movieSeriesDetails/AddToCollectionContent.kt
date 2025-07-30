package com.moscow.cineverse.screen.movieSeriesDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun AddToCollectionContent(
    collectionItems: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(collectionItems) { item ->
            CollectionItem(
                collectionName = item,
                showProgressBars = item == selectedItem,
                onItemClicked = { onItemSelected(item) },
                modifier = modifier
            )

        }

    }
}

@Preview
@Composable
private fun AddToCollectionContentPreview() {
    CineVerseTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen)
                .padding(10.dp)
        ) {
            AddToCollectionContent(
                collectionItems = listOf("Favorites", "Watchlist", "To Rewatch"),
                selectedItem = "Favorites",
                onItemSelected = {}
            )
        }
    }
}