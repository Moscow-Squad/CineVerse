package com.moscow.cineverse.screen.my_collections.create_collection_dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.component.text.AppTextField
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun CreateCollectionDialog(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    viewModel: CreateCollectionDialogViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.uiEffect.collect { event ->
            handleEvents(
                event,
                viewModel::onCreateClick,
                onNavigateBack,
            )
        }
    }
    CreateCollectionDialogContent(state, viewModel, modifier)
}

private fun handleEvents(
    event: CreateCollectionDialogEvent,
    onCreateCollectionClicked: () -> Unit,
    navigateBack: () -> Unit,
) {
    when (event) {
        CreateCollectionDialogEvent.OnCreateCollection -> onCreateCollectionClicked()
        CreateCollectionDialogEvent.OnCancelCollectionCreation -> navigateBack()

    }

}

@Composable
fun CreateCollectionDialogContent(
    state: CreateCollectionDialogUiState,
    interactionListener: CreateCollectionDialogInteractionListener,
    modifier: Modifier = Modifier,
) {
    CineVerseBottomSheet(
        modifier = modifier,
        title = stringResource(R.string.create_new_collection),
        onClose = interactionListener::onCancelClick,
        onDismissRequest = interactionListener::onCancelClick,
    ) {
        Column(modifier = Modifier.padding(top = 16.dp)) {
            MovieText(
                text = stringResource(R.string.collection_name)
            )
            AppTextField(
                value = state.collectionName,
                onValueChange = interactionListener::onCollectionNameChange,
                leadingIcon = com.moscow.cineverse.design_system.R.drawable.outline_folder,
                leadingIconTint = Theme.colors.shade.tertiary,
                placeholder = stringResource(R.string.my_collection_name_hint),
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MovieButton(
                    buttonText = stringResource(R.string.cancel),
                    textColor = Theme.colors.button.onSecondary,
                    textStyle = Theme.textStyle.body.medium.medium,
                    onClick = interactionListener::onCancelClick,
                    buttonColor = Theme.colors.button.secondary,
                    modifier = Modifier.weight(1f)
                )

                MovieButton(
                    buttonText = stringResource(R.string.create),
                    textColor = if (state.collectionName.isEmpty()) Theme.colors.button.onDisabled else Theme.colors.button.onPrimary,
                    textStyle = Theme.textStyle.body.medium.medium,
                    onClick = interactionListener::onCancelClick,
                    buttonColor = if (state.collectionName.isEmpty()) Theme.colors.button.disabled else Theme.colors.button.primary,
                    modifier = Modifier.weight(1f)
                )

            }

        }


    }
}