package com.moscow.cineverse.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.domain.model.Collection
import com.moscow.cineverse.designSystem.component.MessageInfoBox
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieText
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.CollectionItem
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun CollectionsBottomSheet(
    isLoading: Boolean,
    showProcessIndicator: Boolean,
    errorMessage: String,
    collections: List<Collection>,
    onCollectionClick: () -> Unit,
    onAddNewCollectionClick: () -> Unit,
    onDismissBottomSheet: () -> Unit,
    onCloseBottomSheet: () -> Unit,
) {
    when {

        isLoading -> {
            CineVerseBottomSheet(
                title = "",
                onClose = {},
                onDismissRequest = onDismissBottomSheet,
                showCancelIcon = false
            ) {
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
        }

        errorMessage.isNotEmpty() -> {
            CineVerseBottomSheet(
                title = "",
                onClose = {},
                onDismissRequest = onDismissBottomSheet,
                showCancelIcon = false
            ) {
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
                        buttonText = "Retry",
                        textColor = Theme.colors.button.primary,
                        textStyle = Theme.textStyle.title.small,
                        onClick = { }
                    )
                }
            }
        }

        else -> {
            CineVerseBottomSheet(
                title = stringResource(R.string.add_to_collection),
                onClose = onCloseBottomSheet,
                onDismissRequest = onDismissBottomSheet,
                showCancelIcon = collections.isEmpty(),
                onAddNewCollectionClick = onAddNewCollectionClick
            ) {
                if (collections.isEmpty()) {
                    MessageInfoBox(
                        title = "No Collections Yet",
                        description = "Create a new collection to start saving your favorite movies and series.",
                        icon = painterResource(Theme.icons.dueTone.videoLibrary),
                        showButtonsGroup = false,
                        firstButtonText = "",
                        onClickFirstButton = {},
                        secondButtonText = "Create Collection",
                        onClickSecondButton = {
                            /* TODO("should open new bottom sheet to login or to create new collection") */
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(collections) { currentCollection ->
                            CollectionItem(
                                collectionName = currentCollection.name,
                                showProgressBars = showProcessIndicator,
                                onItemClicked = onCollectionClick,
                            )
                        }
                    }
                }
            }
        }
    }
}