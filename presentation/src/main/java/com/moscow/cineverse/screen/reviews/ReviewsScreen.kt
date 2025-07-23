package com.moscow.cineverse.screen.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.rememberAsyncImagePainter
import com.android.domain.model.Review
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieReviewCard
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.mapper.formatReviewDate
import com.moscow.cineverse.mapper.toUi
import com.moscow.cinverse.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReviewsScreen(
    movieId:Int,
    isMovie: Boolean,
    modifier: Modifier = Modifier,
    viewModel: ReviewsViewModel = koinViewModel(),
    navController: NavHostController = LocalNavController.current
    ) {
    val reviewsFlow = viewModel.getPagedReviews(movieId, isMovie).collectAsLazyPagingItems()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { event ->
            when(event){
                ReviewsEffect.NavigateBack -> {
                    navController.popBackStack()
                }
                is ReviewsEffect.ShowError -> {}
            }

        }
    }
    ReviewsContent(
        reviewsFlow,
        viewModel,
        modifier
    )
}
@Composable
fun ReviewsContent(
    reviewsFlow: LazyPagingItems<Review>,
    interactionListener: ReviewsInteractionListener,
    modifier: Modifier = Modifier
) {
    MovieScaffold {
        Column(
            modifier = modifier.background(Theme.colors.background.screen)
        ) {
            MovieAppBar(
                backButtonClick = { interactionListener.onBackPressed() },
                title = stringResource(R.string.top_reviews)
            )

            LazyColumn {
                items(reviewsFlow.itemCount) { index ->
                    val review = reviewsFlow[index]?.toUi()
                    if (review != null) {
                        MovieReviewCard(
                            name = review.name,
                            username = review.username,
                            reviewText = review.reviewContent,
                            rating = review.rate,
                            date = formatReviewDate(review.date),
                            avatar = if (review.userImage.isEmpty()) null else rememberAsyncImagePainter(
                                model = review.userImage
                            ),
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 12.dp
                            )
                        )
                    }
                }

                when (reviewsFlow.loadState.append) {
                    is androidx.paging.LoadState.Loading -> {
                        item {
                            Text(
                                "Loading more...",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    is androidx.paging.LoadState.Error -> {
                        item {
                            Text(
                                "Error loading more data.",
                                modifier = Modifier.padding(16.dp),

                                )
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}
