package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun <T> MovieListSection(
    modifier: Modifier = Modifier,
    title: String,
    movies: List<T>,
    paddingHorizontal: Int = 16,
    onClickShowMore: () -> Unit = {},
    onClickPoster: (T) -> Unit = {},
    movieCardContent: @Composable (T, Modifier, (T) -> Unit) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SectionTitle(
            title = title,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = paddingHorizontal.dp),
            onClick = onClickShowMore
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = paddingHorizontal.dp)
        ) {
            itemsIndexed(movies) { index, movie ->
                movieCardContent(
                    movie,
                    Modifier.width(136.dp),
                    onClickPoster
                )
            }
        }
    }
}