package com.moscow.cineverse.screen.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.common_ui_state.MyCollectionUiState
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun MyCollectionsLayout(
    items: List<MyCollectionUiState>,
    onCollectionClick: (collectionId: Int, collectionName: String) -> Unit,
    onShowMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val itemsRowSize = (items.size + 1) / 2
    Column(modifier = modifier.fillMaxWidth()
        , verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.your_collections),
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary
            )

            Text(
                text = stringResource(R.string.show_more),
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.brand.primary,
                modifier = Modifier.clickable(onClick = onShowMoreClick)
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),

        ) {
            items(items.take(itemsRowSize)) { itemState ->
                MyCollectionCard(
                    modifier = Modifier.width(280.dp).padding(horizontal = 16.dp),
                    state = itemState,
                    onClick = { onCollectionClick(itemState.id, itemState.title) },
                )
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(items.drop(itemsRowSize)) { itemState ->
                MyCollectionCard(
                    modifier = Modifier.width(280.dp).padding(horizontal = 16.dp),
                    state = itemState,
                    onClick = { onCollectionClick(itemState.id, itemState.title) },
                )
            }
        }


    }

}