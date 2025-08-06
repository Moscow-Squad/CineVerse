package com.moscow.cineverse.screen.my_collections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.component.button.MovieFloatingButton
import com.moscow.cineverse.designSystem.component.wrapper.MovieIcon
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.home.components.MyCollectionCard
import com.moscow.cinverse.presentation.R

@Composable
fun MyCollectionsScreen(
    onNavigateBack: () -> Unit,
    navigateToCreateCollectionDialog: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateToCollectionDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyCollectionsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.uiEffect.collect {
            when (it) {
                MyCollectionsEvent.OnNavigateBack -> onNavigateBack.invoke()
                is MyCollectionsEvent.OnNavigateToCollection -> navigateToCollectionDetails(it.collectionId)
                MyCollectionsEvent.OnNavigateToCreateCollection -> navigateToCreateCollectionDialog()
                MyCollectionsEvent.OnStartCollecting -> navigateToExplore()
            }
        }
    }
    MyCollectionsContent(uiState, modifier, viewModel)
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
                    contentPadding = PaddingValues(16.dp),
                    iconSize = 24.dp,
                    buttonSize = 56.dp,
                    useWrapContentSize = true,
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

                state.collections.isEmpty() -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth(.7f)
                    ) {
                        MovieIcon(
                            painter = painterResource(com.moscow.cineverse.design_system.R.drawable.due_tone_video_library),
                            tint = Theme.colors.brand.primary,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(
                                    color = Theme.colors.button.disabled,
                                    shape = CircleShape
                                )
                                .padding(18.dp)
                        )
                        MovieText(
                            text = stringResource(R.string.no_collections_yet),
                            color = Theme.colors.shade.primary,
                            style = Theme.textStyle.title.small,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        MovieText(
                            text = stringResource(R.string.no_collections_yet_description),
                            color = Theme.colors.shade.primary,
                            style = Theme.textStyle.body.small.medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        MovieButton(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .fillMaxWidth(),
                            cornerRadius = Theme.radius.medium,
                            buttonColor = Theme.colors.button.primary,
                            onClick = interactionListener::onStartCollectingClick,
                            buttonText = stringResource(R.string.start_collecting),
                            textColor = Theme.colors.button.onPrimary,
                            textStyle = Theme.textStyle.body.small.medium,
                        )

                    }
                }

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.collections) { collection ->
                        MyCollectionCard(
                            state = collection,
                            onClick = interactionListener::onCollectionClick,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )

                    }
                }
            }
        }


    }
}

@Preview
@Composable
private fun Preview() {

}