package com.moscow.cineverse.designSystem.component.myCollections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme
import kotlinx.coroutines.delay

@Composable
fun SwipeToDeleteItem(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    animationDurationMillis: Long = 400L,
    content: @Composable () -> Unit
) {
    var showContent by remember { mutableStateOf(true) }

    val swipeState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                showContent = false
                true
            } else {
                false
            }
        }
    )

    AnimatedVisibility(
        visible = showContent,
        modifier = modifier,
        exit = shrinkVertically(animationSpec = tween(animationDurationMillis.toInt()))
                + fadeOut(animationSpec = tween(animationDurationMillis.toInt()))
    ) {
        SwipeToDismissBox(
            state = swipeState,
            enableDismissFromStartToEnd = false,
            backgroundContent = { SwipeBackground() },
        ) {
            content()
        }
    }

    LaunchedEffect(showContent, swipeState.currentValue) {
        if (!showContent && swipeState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            delay(animationDurationMillis)
            onDelete()
        }
    }
}

@Composable
private fun SwipeBackground() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(Theme.radius.large))
                .background(Theme.colors.additional.primary.red)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.due_tone_trash),
                contentDescription = "Trash Icon",
                tint = Theme.colors.button.onPrimary,
            )
        }
    }
}

@Preview
@Composable
private fun SwipeToDeleteItemPreview() {

    val initialMovies = listOf(
        "The Dark Knight",
        "The Dark Knight",
        "The Dark Knight",
        "The Dark Knight",
        "The Dark Knight",
        "The Dark Knight",
        "The Dark Knight",
        "The Dark Knight",
        "The Dark Knight",
        "The Dark Knight",
        "The Dark Knight",
        "The Dark Knight"
    )
    var movies by remember { mutableStateOf(initialMovies) }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            count = movies.size,
            key = { movie -> movie }
        ) { currentMovie ->
            SwipeToDeleteItem(
                onDelete = { movies.filterNot { it == movies[currentMovie] } }
            ) {
                Card(modifier = Modifier.size(width = 328.dp, height = 88.dp)) {
                    Text(text = movies[currentMovie])
                }
            }
        }
    }
}