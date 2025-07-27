package com.moscow.cineverse.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.designSystem.utils.noRibbleClick
import com.moscow.cineverse.screen.home.HomeFeaturedItems
import com.moscow.cinverse.presentation.R

@Composable
fun FeaturedMovies(
    displayMovies: List<MediaItemUiState>,
    onMovieClick: (id: Int) -> Unit,
    onShowMoreClick: (type: String) -> Unit,
    type: HomeFeaturedItems,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(type.titleResource),
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary
            )

            Text(
                text = stringResource(R.string.show_more),
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.brand.primary,
                modifier = Modifier.noRibbleClick {

                    onShowMoreClick(type.name)
                }
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(displayMovies) { movie ->
                MoviePosterCard(
                    movie = movie,
                    onMovieClick = onMovieClick,
                )
            }
        }
    }
}
