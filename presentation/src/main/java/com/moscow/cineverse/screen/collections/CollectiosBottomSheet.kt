package com.moscow.cineverse.screen.collections

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.designSystem.component.MessageInfoBox
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieText
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.CollectionItem
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R


@Composable
fun CollectionsBottomSheetScreen(
    viewModel: CollectionsBottomSheetViewModel = hiltViewModel(),
    onAddNewCollectionClick: () -> Unit,
    onCreateCollectionClicked: () -> Unit,
    navigateBack: () -> Unit,
    onLogIn: () -> Unit
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val context = LocalContext.current
    CollectionsBottomSheetContent(
        interactionListener = viewModel,
        onAddNewCollectionClick = onAddNewCollectionClick,
        uiState = uiState,
        onDismissBottomSheet = navigateBack,
        onCloseBottomSheet = navigateBack,
        onNotNow = navigateBack,
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { event ->
            handleEvents(
                event = event,
                onCreateCollectionClicked = onCreateCollectionClicked,// user already log in - navigate to collections screen
                navigateBack = navigateBack,
                context = context,
                onLogIn = onLogIn
            )
        }
    }

}

private fun handleEvents(
    event: CollectionsBottomSheetEffect,
    onCreateCollectionClicked: () -> Unit,
    navigateBack: () -> Unit,
    onLogIn: () -> Unit,
    context: Context
) {
    when (event) {
        CollectionsBottomSheetEffect.OnCreateCollectionClicked -> onCreateCollectionClicked()
        CollectionsBottomSheetEffect.OnLoginClicked -> onLogIn()
        is CollectionsBottomSheetEffect.OnMovieAddedSuccessfully -> {
            Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            navigateBack()
        }
    }

}

@Composable
private fun CollectionsBottomSheetContent(
    onAddNewCollectionClick: () -> Unit,
    onDismissBottomSheet: () -> Unit,
    onCloseBottomSheet: () -> Unit,
    onNotNow: () -> Unit,
    uiState: CollectionsBottomSheetScreenState,
    interactionListener: CollectionsBottomSheetInteractionListener
) {
    CineVerseBottomSheet(
        title = stringResource(R.string.add_to_collection),
        onClose = onCloseBottomSheet,
        onDismissRequest = onDismissBottomSheet,
        showCancelIcon = uiState.collections.isEmpty(),
        onAddNewCollectionClick = onAddNewCollectionClick
    ) {
        when {
            uiState.createCollection -> {
                MessageInfoBox(
                    title = stringResource(R.string.you_re_almost_there),
                    description = stringResource(R.string.log_in_to_save_movies_create_collections_and_get_personalized_recommendations),
                    icon = painterResource(Theme.icons.dueTone.videoLibrary),
                    showButtonsGroup = true,
                    firstButtonText = stringResource(R.string.not_now),
                    onClickFirstButton = onNotNow,
                    secondButtonText = stringResource(R.string.log_in),
                    onClickSecondButton = { interactionListener.navigateToLogin() },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            uiState.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MovieCircularProgressBar(
                        gradientColors = listOf(
                            Theme.colors.brand.primary,
                            Theme.colors.brand.tertiary
                        )
                    )
                }
            }

            uiState.errorMessage.isNotEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MovieText(
                        text = "Error in loading collections",
                        color = Theme.colors.shade.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    MovieButton(
                        buttonText = stringResource(R.string.retry),
                        textColor = Theme.colors.button.primary,
                        textStyle = Theme.textStyle.title.small,
                        onClick = { }
                    )
                }
            }

            else -> {
                if (uiState.collections.isEmpty()) {
                    MessageInfoBox(
                        title = stringResource(R.string.no_collections_yet),
                        description = stringResource(R.string.create_a_new_collection_to_start_saving_your_favorite_movies_and_series),
                        icon = painterResource(Theme.icons.dueTone.videoLibrary),
                        showButtonsGroup = false,
                        firstButtonText = "",
                        onClickFirstButton = {},
                        secondButtonText = stringResource(R.string.create_collection),
                        onClickSecondButton = { interactionListener.onCreateCollectionClicked() },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(uiState.collections) { currentCollection ->
                            CollectionItem(
                                collectionName = currentCollection.name,
                                showProgressBars = currentCollection.isLoading,
                                onItemClicked = {
                                    interactionListener.onCollectionClicked(currentCollection.id)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}