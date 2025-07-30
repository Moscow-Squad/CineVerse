package com.moscow.cineverse.screen.explore.component

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.PillLabel
import com.moscow.cineverse.designSystem.component.search.SearchBar
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabs
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.explore.ExploreInteractionListener
import com.moscow.cineverse.screen.explore.ExploreScreenState

@Composable
fun ExploreTopBar(
    uiState: ExploreScreenState,
    genresState: LazyListState,
    interactionListener: ExploreInteractionListener,
    searchBarVisible: Boolean,
    genresVisible: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.background.screen)
    ) {
        AnimatedVisibility(
            visible = searchBarVisible,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(300)
            ),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(300)
            )
        ) {
            ExploreSearchBarSection(
                uiState = uiState,
                interactionListener = interactionListener,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        AnimatedVisibility(
            visible = !uiState.showSuggestions,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(300)
            ),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(300)
            )
        ) {
            ExploreTabsSection(
                selectedTab = uiState.selectedTab,
                onTabSelected = interactionListener::onTabSelected,
                showAllTabs = uiState.searchKeyWord.isNotEmpty(),
                modifier = Modifier.padding(bottom = if (uiState.shouldShowGenres && genresVisible) 0.dp else 16.dp)
            )
        }

        if (uiState.shouldShowGenres && !uiState.showSuggestions) {
            val alpha by animateFloatAsState(
                targetValue = if (genresVisible) 1f else 0f,
                animationSpec = tween(300),
                label = "genresAlpha"
            )

            AnimatedVisibility(
                visible = genresVisible,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(300)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(300)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Theme.colors.background.screen,
                                    Theme.colors.background.screen.copy(alpha = 0.95f),
                                    Theme.colors.background.screen.copy(alpha = 0.8f),
                                    Color.Transparent
                                ),
                                startY = 0f,
                                endY = 80f
                            )
                        )
                        .alpha(alpha)
                ) {
                    GenresRowContent(
                        uiState = uiState,
                        genresState = genresState,
                        interactionListener = interactionListener
                    )
                }
            }
        }
    }
}

@Composable
private fun ExploreSearchBarSection(
    uiState: ExploreScreenState,
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val focusState = remember { mutableStateOf(false) }

    BackHandler(enabled = uiState.showSuggestions) {
        interactionListener.onCancelButtonClicked()
        focusState.value = false
    }

    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        value = uiState.searchKeyWord,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onNext = { interactionListener.onKeyboardClick() },
            onSearch = {
                if (uiState.searchKeyWord.isBlank())
                    Toast.makeText(context, "Search can't be empty!", Toast.LENGTH_SHORT).show()
                else interactionListener.onSearchQuery()
            }
        ),
        onValueChange = { interactionListener.onSearchValueChange(it) },
        onCancelButtonClicked = { interactionListener.onCancelButtonClicked() },
        onFirstFocus = { interactionListener.onSearchBarClickedOn() },
        trailingIcon = {
            VoiceRecognitionIcon(
                modifier = Modifier.size(20.dp),
                onResult = { interactionListener.onSearchWordDetected(it) },
                onError = {}
            )
        },
        focusState = focusState
    )
}

@Composable
private fun ExploreTabsSection(
    selectedTab: ExploreTabsPages,
    onTabSelected: (ExploreTabsPages) -> Unit,
    showAllTabs: Boolean,
    modifier: Modifier = Modifier
) {
    ExploreTabs(
        selectedTab = selectedTab,
        onTabSelected = onTabSelected,
        showAllTabs = showAllTabs,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun GenresRowContent(
    uiState: ExploreScreenState,
    genresState: LazyListState,
    interactionListener: ExploreInteractionListener
) {
    val selectedGenre = if (uiState.selectedTab == ExploreTabsPages.MOVIES) {
        uiState.selectedMovieGenre
    } else {
        uiState.selectedSeriesGenre
    }

    LazyRow(
        state = genresState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 12.dp)
    ) {
        items(uiState.genres) { genre ->
            PillLabel(
                text = genre.name,
                isActive = selectedGenre == genre.id,
                onClick = {
                    if (uiState.selectedTab == ExploreTabsPages.SERIES) {
                        interactionListener.onSeriesGenreSelected(genre.id)
                    } else {
                        interactionListener.onMovieGenreSelected(genre.id)
                    }
                }
            )
        }
    }
}