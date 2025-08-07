package com.moscow.cineverse.screen.cast_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.screen.cast_details.CastDetailsUiState.SocialMediaLinks
import com.moscow.domain.model.ActorDetails
import com.moscow.domain.model.Movie
import com.moscow.domain.repository.blur.BlurProvider
import com.moscow.domain.usecase.actor.GetActorBestMoviesUseCase
import com.moscow.domain.usecase.actor.GetActorDetailsUseCase
import com.moscow.domain.usecase.actor.GetActorGalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastDetailsViewModel @Inject constructor(
    private val getActorDetailsUseCase: GetActorDetailsUseCase,
    private val getActorGalleryUseCase: GetActorGalleryUseCase,
    private val getActorBestMoviesUseCase: GetActorBestMoviesUseCase,
    private val blurProvider: BlurProvider,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CastDetailsUiState, CastDetailsEffect>(CastDetailsUiState()),
    CastDetailsInteractionListener {
    val actorId = savedStateHandle.get<Int>(CastDetailsRoute.CAST_ID) ?: 0

    init {
        loadActorDetails()
        observeBlur()
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(enableBlur = enableBlur) }
            }
        }
    }

    private fun loadActorDetails() {
        launchWithResult(
            action = {
                val actorDetails = getActorDetailsUseCase.invoke(actorId)
                actorDetails
            },
            onSuccess = ::onActorDetailsSuccess,
            onError = ::onActorDetailsError,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onActorDetailsSuccess(actorDetails: ActorDetails) {
        updateState { currentState ->
            currentState.copy(
                actorDetails = actorDetails,
                isLoading = false,
                shouldShowError = false,
                errorMessage = "",
                socialMediaLinks = SocialMediaLinks(
                    youtube = actorDetails.youtubeLink.takeIf { it.isNotEmpty() },
                    facebook = actorDetails.facebookLink.takeIf { it.isNotEmpty() },
                    instagram = actorDetails.instagramLink.takeIf { it.isNotEmpty() }
                ),
                isContentEmpty = false
            )
        }

        loadGalleryImages()
        loadBestOfMovies()
    }

    private fun loadGalleryImages() {
        updateState { currentState ->
            currentState.copy(isLoadingImages = true)
        }

        viewModelScope.launch {
            try {
                getActorGalleryUseCase.invoke(actorId).forEach { image ->
                    updateState { currentState ->
                        currentState.copy(
                            images = currentState.images + image,
                            isLoadingImages = false
                        )
                    }
                }
            } catch (_: Exception) {
                updateState { currentState ->
                    currentState.copy(
                        images = emptyList(),
                        isLoadingImages = false
                    )
                }
            }
        }
    }

    private fun loadBestOfMovies() {
        updateState { currentState ->
            currentState.copy(isLoadingMovies = true)
        }

        viewModelScope.launch {
            try {
                val movies = getActorBestMoviesUseCase.invoke(actorId)
                updateState { currentState ->
                    currentState.copy(
                        movies = movies,
                        isLoadingMovies = false
                    )
                }
            } catch (_: Exception) {
                updateState { currentState ->
                    currentState.copy(
                        movies = emptyList(),
                        isLoadingMovies = false
                    )
                }
            }
        }
    }

    private fun onActorDetailsError(throwable: Throwable) {
        updateState { currentState ->
            currentState.copy(
                isLoading = false,
                shouldShowError = true,
                errorMessage = throwable.message ?: "Failed to load actor details",
                isContentEmpty = false
            )
        }
        sendEvent(CastDetailsEffect.ShowError(throwable.message ?: "Failed to load actor details"))
    }

    private fun onLoading() {
        updateState { currentState ->
            currentState.copy(
                isLoading = true,
                shouldShowError = false,
                errorMessage = ""
            )
        }
    }

    private fun onFinally() {
        updateState { currentState ->
            currentState.copy(isLoading = false)
        }
    }

    override fun onBackPressed() {
        sendEvent(CastDetailsEffect.NavigateBack)
    }

    override fun onSocialMediaClick(platform: String, url: String) {
        val socialMediaLinks = uiState.value.socialMediaLinks
        val targetUrl = when (platform.lowercase()) {
            "youtube" -> socialMediaLinks.youtube ?: url
            "facebook" -> socialMediaLinks.facebook ?: url
            "instagram" -> socialMediaLinks.instagram ?: url
            else -> url
        }
        sendEvent(CastDetailsEffect.OpenSocialMedia(platform, targetUrl))
    }

    override fun onRefresh() {
        updateState { currentState ->
            currentState.copy(
                actorDetails = null,
                movies = emptyList(),
                images = emptyList(),
                socialMediaLinks = SocialMediaLinks(),
                shouldShowError = false,
                errorMessage = "",
                isContentEmpty = false,
                isLoadingMovies = false,
                isLoadingImages = false
            )
        }
        loadActorDetails()
    }


    override fun onMovieClick(movie: Movie) {
        sendEvent(CastDetailsEffect.NavigateToMovie(movie.id))
    }

    override fun onShowMoreMovies() {
        sendEvent(
            CastDetailsEffect.NavigateToFullMovieList(
                actorId,
                uiState.value.actorDetails?.name ?: ""
            )
        )
    }

    override fun onShowMoreGallery() {
        sendEvent(
            CastDetailsEffect.NavigateToFullGallery(
                actorId,
                uiState.value.actorDetails?.name ?: ""
            )
        )
    }
}