package com.moscow.cineverse.screen.castDetails

import androidx.lifecycle.viewModelScope
import com.android.domain.model.ActorDetails
import com.android.domain.model.Movie
import com.android.domain.usecase.actor.GetActorBestMoviesUseCase
import com.android.domain.usecase.actor.GetActorDetailsUseCase
import com.android.domain.usecase.actor.GetActorGalleryUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.cast_details.SocialMediaLinks
import kotlinx.coroutines.launch

class CastDetailsViewModel(
    private val getActorDetailsUseCase: GetActorDetailsUseCase,
    private val getActorGalleryUseCase: GetActorGalleryUseCase,
    private val getActorBestMoviesUseCase: GetActorBestMoviesUseCase,
    private val actorId: Int,
) : BaseViewModel<CastDetailsUiState, CastDetailsEvent>(CastDetailsUiState()),
    CastDetailsInteractionListener {

    init {
        loadActorDetails()
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
            } catch (e: Exception) {
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
            } catch (e: Exception) {
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
        sendEvent(CastDetailsEvent.ShowError(throwable.message ?: "Failed to load actor details"))
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
        sendEvent(CastDetailsEvent.NavigateBack)
    }

    override fun onSocialMediaClick(platform: String, url: String) {
        val socialMediaLinks = uiState.value.socialMediaLinks
        val targetUrl = when (platform.lowercase()) {
            "youtube" -> socialMediaLinks.youtube ?: url
            "facebook" -> socialMediaLinks.facebook ?: url
            "instagram" -> socialMediaLinks.instagram ?: url
            else -> url
        }
        sendEvent(CastDetailsEvent.OpenSocialMedia(platform, targetUrl))
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

    // Additional methods for handling interactions
    override fun onMovieClick(movie: Movie) {
        sendEvent(CastDetailsEvent.NavigateToMovie(movie.id))
        // Handle movie click - you can add navigation or other actions
        // For example: sendEvent(CastDetailsEvent.NavigateToMovie(movie.id))
    }

    override fun onShowMoreMovies() {
        sendEvent(
            CastDetailsEvent.NavigateToFullMovieList(
                actorId,
                uiState.value.actorDetails?.name ?: ""
            )
        )
        // Handle show more movies - you can add navigation to full movie list
        // For example: sendEvent(CastDetailsEvent.NavigateToFullMovieList(actorId))
    }

    override fun onShowMoreGallery() {
        sendEvent(
            CastDetailsEvent.NavigateToFullGallery(
                actorId,
                uiState.value.actorDetails?.name ?: ""
            )
        )
        // Handle show more gallery - you can add navigation to full gallery
        // For example: sendEvent(CastDetailsEvent.NavigateToFullGallery(actorId))
    }
}