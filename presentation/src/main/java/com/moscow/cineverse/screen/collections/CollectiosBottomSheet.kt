package com.moscow.cineverse.screen.collections

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.component.card.MessageInfoCard
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.details.common.CollectionItem
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
        is CollectionsBottomSheetEffect.OnItemAddedSuccessfully -> {
            Toast.makeText(context, "item added successfully", Toast.LENGTH_SHORT).show()
            navigateBack()
        }
        is CollectionsBottomSheetEffect.OnItemAddedFailed -> {
            Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun CollectionsBottomSheetContent(
    onDismissBottomSheet: () -> Unit,
    onCloseBottomSheet: () -> Unit,
    onNotNow: () -> Unit,
    uiState: CollectionsBottomSheetScreenState,
    interactionListener: CollectionsBottomSheetInteractionListener
) {
    CineVerseBottomSheet(
        title = if (uiState.isLoggedIn == true) stringResource(R.string.add_to_collection) else "",
        onClose = onCloseBottomSheet,
        onDismissRequest = onDismissBottomSheet,
        showCancelIcon = uiState.collections.isEmpty() && uiState.isLoggedIn == true,
        onAddNewCollectionClick = { interactionListener.onAddNewCollectionClick() }
    ) {
        Log.d("TAG", "isLogged: ${uiState.isLoggedIn} ")
        when {

            uiState.isLoggedIn == false -> {
                MessageInfoCard(
                    title = stringResource(R.string.you_re_almost_there),
                    description = stringResource(R.string.log_in_to_save_movies_create_collections_and_get_personalized_recommendations),
                    icon = painterResource(R.drawable.due_tone_login),
                    showButtonsGroup = true,

                    firstButtonText = stringResource(R.string.not_now),
                    onClickFirstButton = onNotNow,
                    secondButtonText = stringResource(R.string.log_in),
                    onClickSecondButton = { interactionListener.navigateToLogin() },
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

            uiState.errorMessage != null -> {
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
                    MessageInfoCard(
                        title = stringResource(R.string.no_collections_yet),
                        description = stringResource(R.string.create_a_new_collection_to_start_saving_your_favorite_movies_and_series),
                        icon = painterResource(R.drawable.due_tone_video_library),
                        showButtonsGroup = false,
                        firstButtonText = "",
                        onClickFirstButton = {},
                        secondButtonText = stringResource(R.string.create_collection),
                        onClickSecondButton = { interactionListener.onCreateCollectionClicked() },
                        firstButtonLoading = uiState.isLoading
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .height(170.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(uiState.collections) { currentCollection ->
                            CollectionItem(
                                collectionName = currentCollection.title,
                                showProgressBars = currentCollection.isLoading,
                                onItemClicked = {
                                    interactionListener.onCollectionClicked(currentCollection.id)
                                },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}