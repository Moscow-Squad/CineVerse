package com.moscow.cineverse.screen.movie_details

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieReviewsScreen(viewModel: MovieDetailsViewModel = koinViewModel()) {
    val reviews = viewModel.reviewsFlow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(reviews.itemCount) { index ->
            if (reviews[index] != null) {
                Text(text = reviews[index]!!.author, fontWeight = FontWeight.Bold)
                Text(text = reviews[index]!!.content)
                HorizontalDivider()
            }
        }

        reviews.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }
                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }
                loadState.refresh is LoadState.Error -> {
                    item { Text("Failed to load data") }
                }
            }
        }
    }
}
