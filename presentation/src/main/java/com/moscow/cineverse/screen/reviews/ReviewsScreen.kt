package com.moscow.cineverse.screen.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.rememberAsyncImagePainter
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.mapper.formatDate

import com.moscow.cineverse.mapper.toUi
import com.moscow.cineverse.screen.details.common.MovieReviewCard
import com.moscow.cinverse.presentation.R
import com.moscow.domain.model.Review

@Composable
fun ReviewsScreen(
    modifier: Modifier = Modifier,
    viewModel: ReviewsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val reviewsFlow = viewModel.getPagedReviews().collectAsLazyPagingItems()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { event ->
            when (event) {
                ReviewsEffect.NavigateBack -> {
                    navigateBack()
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
                            date = if (review.date != null) review.date.formatDate() else "",
                            avatar = if (review.userImageUrl.isEmpty()) null else rememberAsyncImagePainter(
                                model = review.userImageUrl
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
                    is LoadState.Loading -> {
                        item{
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(214.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                MovieCircularProgressBar()
                            }
                        }
                    }

                    is LoadState.Error -> {
                        item{
                            NoInternetScreen(
                                onRetry = { interactionListener.onRetry() }
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}
