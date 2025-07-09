package com.moscow.cineverse.designSystem.component.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.CollectionItem
import com.moscow.cineverse.designSystem.component.SectionTitle

@Composable
fun YourCollectionSection(
    collections: List<Collection>, // ToDo: Will be replace by ui state
    onSectionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionTitle(
            title = stringResource(R.string.you_collections_section),
            onClick = onSectionClick
        )
        CollectionSection(
            collections = collections,
            onSectionClick = onSectionClick
        )
    }

}

data class Collection(
    val title: String,
    val size: Int,
)

@Composable
private fun CollectionSection(
    collections: List<Collection>, // ToDo: Will be replace by ui state
    onSectionClick: () -> Unit
) {
    FlowRow(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 5
    ) {
        collections.forEach { item ->
            CollectionItem(
                modifier = Modifier
                    .width(280.dp)
                    .clickable { onSectionClick },
                title = item.title,
                description = "${item.size} shows"
            )
        }
    }
}

@Preview
@Composable
private fun YourCollectionSectionPreview() {
    val collections = listOf(
        Collection(
            title = "My Favorite TV Shows",
            size = 5
        ),
        Collection(
            title = "Animated Series",
            size = 4
        ),
        Collection(
            title = "My Watchlist",
            size = 5
        ),
        Collection(
            title = "Cristian Bale Movies",
            size = 5
        ),
    )
    YourCollectionSection(
        collections = collections,
        onSectionClick = {}
    )
}