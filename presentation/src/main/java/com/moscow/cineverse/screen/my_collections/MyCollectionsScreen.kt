package com.moscow.cineverse.screen.my_collections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.moscow.cineverse.component.EmptyCollection
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.button.MovieFloatingButton
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.home.components.MyCollectionCard
import com.moscow.cinverse.presentation.R

@Composable
fun MyCollectionsScreen(
    onNavigateBack: () -> Unit,
    navigateToCreateCollectionDialog: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateToCollectionDetails: (collectionId: Int, collectionName: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyCollectionsViewModel = hiltViewModel(),
    currentBackStackEntry: NavBackStackEntry?,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.uiEffect.collect {
            when (it) {
                MyCollectionsEvent.OnNavigateBack -> onNavigateBack.invoke()
                is MyCollectionsEvent.OnNavigateToCollection -> navigateToCollectionDetails(
                    it.collectionId,
                    it.collectionName
                )

                MyCollectionsEvent.OnNavigateToCreateCollection -> navigateToCreateCollectionDialog()
                MyCollectionsEvent.OnStartCollecting -> navigateToExplore()
            }
        }
    }
    MyCollectionsContent(uiState, modifier, viewModel)
    ListenOnNewCollection(
        currentBackStackEntry = currentBackStackEntry,
        insertNewCollection = viewModel::insertNewCollection
    )
}

@Composable
private fun ListenOnNewCollection(
    currentBackStackEntry: NavBackStackEntry?,
    insertNewCollection: (collectionId: Int, collectionName: String) -> Unit,
) {
    val collectionName = currentBackStackEntry?.savedStateHandle
        ?.getStateFlow<String?>("collection_name", null)
        ?.collectAsStateWithLifecycle()?.value

    val collectionId = currentBackStackEntry?.savedStateHandle
        ?.getStateFlow<Int?>("collection_Id", null)
        ?.collectAsStateWithLifecycle()?.value

    LaunchedEffect(collectionId) {
        if (collectionId == null) {
            return@LaunchedEffect
        } else {
            currentBackStackEntry.savedStateHandle.remove<Int>(key = "collection_Id")
            currentBackStackEntry.savedStateHandle.remove<String>(key = "collection_name")
            insertNewCollection(collectionId, collectionName.toString())
        }
    }
}

@Composable
fun MyCollectionsContent(
    state: MyCollectionsUiState,
    modifier: Modifier = Modifier,
    interactionListener: MyCollectionsInteractionListener,
) {
    MovieScaffold(
        movieAppBar = {
            MovieAppBar(
                title = stringResource(R.string.my_collections),
                showDivider = false,
                showBackButton = true,
                backButtonClick = { interactionListener.onBackClick() }
            )
        },
        movieFloatingActionButton = {
            if (state.collections.isNotEmpty())
                MovieFloatingButton(
                    buttonIcon = com.moscow.cineverse.design_system.R.drawable.outline_plus,
                    iconSize = 24.dp,
                    buttonSize = 56.dp,
                    onClick = interactionListener::onCreateCollectionClick,
                    backgroundColor = Theme.colors.brand.primary,
                    iconColor = Theme.colors.button.onPrimary,
                )
        }
    ) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when {
                state.isLoading -> MovieCircularProgressBar(
                    modifier = Modifier.align(Alignment.Center),
                    gradientColors = listOf(
                        Theme.colors.brand.primary,
                        Theme.colors.brand.tertiary
                    )
                )

                state.errorMessage != null -> {
                    ErrorContent(
                        errorMessage = state.errorMessage,
                        onRetry = interactionListener::onRetry,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Theme.colors.background.screen)
                    )
                }

                state.collections.isEmpty() -> {
                    EmptyCollection(onStartCollectingClick = interactionListener::onStartCollectingClick)
                }

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.collections) { collection ->
                        MyCollectionCard(
                            state = collection,
                            onClick = {
                                interactionListener.onCollectionClick(
                                    collection.id,
                                    collection.title
                                )
                            },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        )

                    }
                }
            }
        }
    }
}