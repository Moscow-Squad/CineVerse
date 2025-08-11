package com.moscow.cineverse.screen.cast_details

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.MovieListSection
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.component.SectionTitle
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.mapper.toFormattedBirthDate
import com.moscow.cineverse.mapper.toMediaItemUi
import com.moscow.cineverse.screen.cast_details.CastDetailsUiState.SocialMediaLinks
import com.moscow.cineverse.screen.cast_details.composable.CastDetailsEffectHandlerWithContext
import com.moscow.cineverse.screen.cast_details.composable.EmptyContent
import com.moscow.cineverse.screen.cast_details.composable.LoadingContent
import com.moscow.cineverse.screen.cast_details.gallery.GallerySection
import com.moscow.cineverse.screen.movie_details.InfoSection
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cineverse.utlis.noRibbleClick
import com.moscow.cinverse.presentation.R

@Composable
fun CastDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: CastDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToCastBestOfMovie: (Int, String) -> Unit,
    navigateToCastGallery: (Int, String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            CastDetailsEffectHandlerWithContext.handleEffectWithContext(
                effect,
                context,
                navigateBack,
                navigateToMovieDetails,
                navigateToCastBestOfMovie,
                navigateToCastGallery
            )
        }
    }

    MovieScaffold {
        Column(modifier = modifier.fillMaxSize()) {
            MovieAppBar(
                title = "",
                backButtonClick = navigateBack,
                showBackButton = true,
                showAddButton = false,
                showLogo = false,
                showDivider = true
            )
            CastDetailsContent(
                modifier = Modifier.weight(1f), uiState = uiState, interactionListener = viewModel
            )
        }
    }
}

@Composable
private fun CastDetailsContent(
    modifier: Modifier = Modifier,
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
) {
    when {
        uiState.isLoading -> LoadingContent(modifier)
        uiState.shouldShowError -> ErrorContent(
            errorMessage = uiState.errorMessage,
            onRetry = interactionListener::onRefresh,
            modifier = modifier
        )

        uiState.isContentEmpty -> EmptyContent(modifier)
        uiState.actorDetails != null -> ActorDetailsContent(uiState, interactionListener, modifier)
    }
}

@Composable
private fun ActorDetailsContent(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            ActorMainDetailsSection(uiState, interactionListener, Modifier.fillMaxWidth())
        }
        item {
            ActorMoviesSection(uiState, interactionListener, Modifier.padding(top = 4.dp))
        }
        item {
            ActorGallerySection(uiState, interactionListener, Modifier.padding(vertical = 16.dp))
        }
        item {
            ActorBiographySection(uiState, Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun ActorMoviesSection(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    if (uiState.movies.isNotEmpty()) {
        MovieListSection(
            title = stringResource(
                R.string.best_of,
                uiState.actorDetails?.name.orEmpty()
            ),
            movies = uiState.movies.take(10),
            paddingHorizontal = 20,
            onClickShowMore = interactionListener::onShowMoreMovies,
            onClickPoster = interactionListener::onMovieClick,
            movieCardContent = { movie, cardModifier, onMovieClick ->
                MoviePosterCard(
                    movie = movie.toMediaItemUi(),
                    viewMode = ViewMode.GRID,
                    showRating = true,
                    enableBlur = uiState.enableBlur,
                    onMovieClick = { onMovieClick(movie) },
                    showTitle = true,
                    modifier = cardModifier
                )
            },
            modifier = modifier
        )
    }
}

@Composable
fun ActorGallerySection(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    if (uiState.images.isNotEmpty() && uiState.images.size >= 3) {
        Column(
            modifier = modifier
        ) {
            SectionTitle(
                title = stringResource(R.string.gallery),
                onClick = {
                    interactionListener.onShowMoreGallery()
                },
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            GallerySection(
                images = uiState.images.take(3),
                enableBlur = uiState.enableBlur,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun ActorBiographySection(
    uiState: CastDetailsUiState, modifier: Modifier = Modifier
) {
    uiState.actorDetails?.let { actorDetails ->
        if (actorDetails.biography.isNotEmpty()) {
            Box(
                modifier = modifier
            ) {
                InfoSection(
                    title = stringResource(R.string.biography),
                    description = actorDetails.biography,
                    showGenres = false,
                    maxDescriptionLines = Int.MAX_VALUE,
                    paddingBetween = 8.dp,
                    modifier = Modifier.padding(16.dp),
                    showRating = false
                )
            }
        }
    }
}

@Composable
fun ActorMainDetailsSection(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    uiState.actorDetails?.let { actor ->
        MainDetails(
            modifier = modifier,
            profileImage = actor.profileImg,
            name = actor.name,
            date = stringResource(
                R.string.born_on,
                actor.birthDate.toFormattedBirthDate()
            ),
            location = actor.placeOfBirth,
            scrollState = null,
            socialMediaLinks = uiState.socialMediaLinks,
            enableBlur = uiState.enableBlur,
            onSocialMediaClick = interactionListener::onSocialMediaClick,
        )
    }
}

@Composable
fun MainDetails(
    profileImage: String,
    name: String,
    date: String,
    location: String,
    scrollState: ScrollState?,
    socialMediaLinks: SocialMediaLinks,
    enableBlur: String,
    modifier: Modifier = Modifier,
    onSocialMediaClick: (platform: String, url: String) -> Unit = { _, _ -> },
) {
    val isCollapsed by remember {
        derivedStateOf {
            scrollState?.value?.let { it > 100 } == true
        }
    }

    val imageSize by animateDpAsState(
        targetValue = if (isCollapsed) 48.dp else 80.dp, animationSpec = tween(durationMillis = 300)
    )

    val cornerSize by animateDpAsState(
        targetValue = if (isCollapsed) 999.dp else 16.dp,
        animationSpec = tween(durationMillis = 300)
    )

    Card(
        shape = RoundedCornerShape(Theme.radius.extraLarge),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card),
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 16.dp, top = 16.dp, end = 2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                SafeImageViewer(
                    imageUrl = profileImage,
                    modifier = Modifier
                        .width(68.dp)
                        .size(imageSize)
                        .clip(if (isCollapsed) CircleShape else RoundedCornerShape(cornerSize)),
                    isBlurEnabled = enableBlur,
                    placeholderContent = { RemoteImagePlaceholder() },
                    errorContent = { RemoteImagePlaceholder() },
                    onBlurContent = {
                        OnBlurContent(isAddedText = false)
                    })
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text(
                        text = name,
                        color = Theme.colors.shade.primary,
                        style = Theme.textStyle.title.medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    AnimatedVisibility(visible = !isCollapsed) {
                        Column {
                            TextWithIcon(
                                icon = R.drawable.outline_cake,
                                text = date,
                                textColour = Theme.colors.shade.secondary,
                                iconTint = Theme.colors.shade.secondary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            if (location.isNotBlank()) TextWithIcon(
                                icon = R.drawable.outline_location,
                                text = location,
                                iconTint = Theme.colors.shade.secondary,
                                textColour = Theme.colors.shade.secondary,
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SocialMediaPill(
                    name = stringResource(R.string.youtube),
                    iconRes = R.drawable.colored_youtube,
                    url = socialMediaLinks.youtube,
                    onClick = { onSocialMediaClick("youtube", it) },
                    modifier = Modifier.weight(1f)
                )

                SocialMediaPill(
                    name = stringResource(R.string.facebook),
                    iconRes = R.drawable.colored_facebook,
                    url = socialMediaLinks.facebook,
                    onClick = { onSocialMediaClick("facebook", it) },
                    modifier = Modifier.weight(1f)
                )

                SocialMediaPill(
                    name = stringResource(R.string.instagram),
                    iconRes = R.drawable.colored_instagram,
                    url = socialMediaLinks.instagram,
                    onClick = { onSocialMediaClick("instagram", it) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SocialMediaPill(
    name: String,
    iconRes: Int,
    url: String?,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (name.lowercase()) {
        "youtube" -> Theme.colors.shade.quinary to Theme.colors.shade.primary
        "facebook" -> Theme.colors.shade.quinary to Theme.colors.shade.primary
        "instagram" -> Theme.colors.shade.quinary to Theme.colors.shade.primary
        else -> Theme.colors.shade.quinary to Theme.colors.shade.primary
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(backgroundColor)
            .noRibbleClick { url?.let { onClick(it) } }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                modifier = Modifier.size(16.dp),
                contentDescription = "$name icon"
            )

            Text(
                text = name,
                color = textColor,
                style = Theme.textStyle.label.medium.medium,
            )
        }
    }
}

@Composable
fun TextWithIcon(
    text: String,
    @DrawableRes icon: Int,
    textColour: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier
                .size(20.dp)
                .padding(end = 4.dp)

        )
        Text(
            text = text,
            color = textColour,
            style = Theme.textStyle.label.medium.regular,
        )
    }
}
