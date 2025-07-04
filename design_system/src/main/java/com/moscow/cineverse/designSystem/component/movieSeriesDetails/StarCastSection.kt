package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun StarCastSection(
    modifier: Modifier = Modifier,
    seeMore: () -> Unit,
    cast: List<CastMember>
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.star_cast),
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary,
            )
            Text(
                text = stringResource(R.string.show_more),
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.brand.primary,
                modifier = Modifier.clickable { seeMore() }
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(cast.chunked(2)) { rowCast ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowCast.forEach { castMember ->
                        CastCard(castMember = castMember)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun StarCastSectionPreview() {
    CineVerseTheme {
        StarCastSection(
            modifier = Modifier
                .background(Theme.colors.background.screen)
                .padding(10.dp),
            seeMore = {},
            cast = listOf(
                CastMember(
                    realName = "John Doe",
                    nameInMovie = "John",
                    imageUrl = "https://wror.com/uploads/2025/05/GettyImages-1511406162.jpg?format=auto&optimize=high&width=1440"
                ),
                CastMember(
                    realName = "Jane Smith",
                    nameInMovie = "Jane",
                    imageUrl = "https://wror.com/uploads/2025/05/GettyImages-1511406162.jpg?format=auto&optimize=high&width=1440"
                ),
                CastMember(
                    realName = "Alice Johnson",
                    nameInMovie = "Alice",
                    imageUrl = "https://wror.com/uploads/2025/05/GettyImages-1511406162.jpg?format=auto&optimize=high&width=1440"
                ),
                CastMember(
                    realName = "Bob Brown",
                    nameInMovie = "Bob",
                    imageUrl = null
                ),
                CastMember(
                    realName = "Charlie White",
                    nameInMovie = "Charlie",
                    imageUrl = null
                ),

                )
        )
    }
}