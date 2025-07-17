package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun <T> StarCastSection(
    title: String,
    cast: List<T>,
    onSeeMoreClick: () -> Unit,
    castContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    showMoreText: String = stringResource(R.string.show_more),

) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary,
            )
            Text(
                text = showMoreText,
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.brand.primary,
                modifier = Modifier.clickable { onSeeMoreClick() }
            )
        }

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


@Preview
@Composable
private fun StarCastSectionPreview() {

}