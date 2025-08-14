package com.moscow.cineverse.screen.details.movie_details.component


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.moscow.cineverse.common_ui_state.ReviewUiState
import com.moscow.cineverse.component.SectionTitle
import com.moscow.cineverse.mapper.formatDate
import com.moscow.cineverse.screen.details.common.MovieReviewCard

@Composable
fun MovieReviewsSection(
    title: String,
    reviews: List<ReviewUiState>?,
    modifier: Modifier = Modifier,
    onShowMoreClicked: () -> Unit
) {
    if (!reviews.isNullOrEmpty()) {
        SectionTitle(
            title = title,
            onClick = { onShowMoreClicked() },
            modifier = modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp,
            )
        )

        reviews.forEach { review ->
            MovieReviewCard(
                review.name,
                review.username,
                review.reviewContent,
                review.rate,
                review.date?.formatDate() ?: "",
                if (review.userImageUrl.isEmpty()) null else rememberAsyncImagePainter(model = review.userImageUrl),
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp
                )
            )
        }
    } else {
        Spacer(modifier = Modifier.height(50.dp))
    }
}
