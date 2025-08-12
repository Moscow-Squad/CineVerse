package com.moscow.cineverse.screen.my_collections.create_collection_dialog

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    onNavigateBack: (collectionId: Int?, collectionName: String?) -> Unit,
    viewModel: CreateCollectionDialogViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.uiEffect.collect { event ->
            handleEvents(
                event,
                viewModel::onCreateClick,
                navigateBack = { onNavigateBack(state.collectionId, state.collectionName) },
                context = context
            )
        }
    }
    CreateCollectionDialogContent(state, viewModel, modifier)
}

private fun handleEvents(
    event: CreateCollectionDialogEvent,
    onCreateCollectionClicked: () -> Unit,
    navigateBack: () -> Unit,
    context: Context,
) {
    when (event) {
        CreateCollectionDialogEvent.OnCancelCollectionCreation -> navigateBack()
        is CreateCollectionDialogEvent.OnAddCollectionFailed -> {
            Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
        }

        is CreateCollectionDialogEvent.OnCollectionAddedSuccessfully -> {
            Toast.makeText(context, context.getString(R.string.success), Toast.LENGTH_SHORT).show()
            navigateBack()
        }
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
        Column {
            MovieText(
                text = stringResource(R.string.collection_name)
            )
            AppTextField(
                value = state.collectionName,
                onValueChange = interactionListener::onCollectionNameChange,
                leadingIcon = R.drawable.outline_folder,
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
                    onClick = interactionListener::onCreateClick,
                    buttonColor = if (state.collectionName.isEmpty()) Theme.colors.button.disabled else Theme.colors.button.primary,
                    modifier = Modifier.weight(1f),
                    isLoading = state.isLoading
                )

            }

        }


    }
}