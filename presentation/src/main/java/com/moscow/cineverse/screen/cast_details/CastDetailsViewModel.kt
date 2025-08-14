package com.moscow.cineverse.screen.cast_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.base.handleException
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.screen.cast_details.CastDetailsUiState.SocialMediaLinks
import com.moscow.domain.model.Actor
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

    private fun onActorDetailsSuccess(actor: Actor) {
//        updateState { currentState ->
//            currentState.copy(
//                actor = actor,
//                isLoading = false,
//                shouldShowError = false,
//                errorMessage = 0,
//                socialMediaLinks = SocialMediaLinks(
//                    youtube = actor.youtubeLink.takeIf { it.isNotEmpty() },
//                    facebook = actor.facebookLink.takeIf { it.isNotEmpty() },
//                    instagram = actor.instagramLink.takeIf { it.isNotEmpty() },
//                    twitter = actorDetails.twitterLink,
//                    tiktok = actorDetails.tiktokLink
//                ),
//                isContentEmpty = false
//            )
//        }

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
        launchWithResult(
            action = { getActorBestMoviesUseCase(actorId) },
            onSuccess = { movies ->
//                updateState { currentState ->
//                    currentState.copy(
//                        movies = movies.,
//                        isLoadingMovies = false
//                    )
//                }
            },
            onError = { e ->
                updateState { currentState ->
                    currentState.copy(
                        isLoading = false,
                        shouldShowError = true,
                        errorMessage = (e as Exception).handleException(),
                        movies = emptyList(),
                        isLoadingMovies = false
                    )
                }
                sendEvent(CastDetailsEffect.ShowError)
            },
            onStart = {
                updateState { currentState ->
                    currentState.copy(isLoadingMovies = true)
                }
            },
        )
    }

    private fun onActorDetailsError(msg: Int) {
        updateState { currentState ->
            currentState.copy(
                isLoading = false,
                shouldShowError = true,
                errorMessage = msg,
                isContentEmpty = false
            )
        }
        sendEvent(CastDetailsEffect.ShowError)
    }

    private fun onLoading() {
        updateState { currentState ->
            currentState.copy(
                isLoading = true,
                shouldShowError = false,
                errorMessage = 0
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
            "youtube" -> socialMediaLinks.youtube
            "facebook" -> socialMediaLinks.facebook
            "instagram" -> socialMediaLinks.instagram
            "twitter" -> socialMediaLinks.twitter
            "tiktok" -> socialMediaLinks.tiktok
            else -> url
        }
        sendEvent(CastDetailsEffect.OpenSocialMedia(platform, targetUrl))
    }

    override fun onRefresh() {
        updateState { currentState ->
            currentState.copy(
                actor = null,
                movies = emptyList(),
                images = emptyList(),
                socialMediaLinks = SocialMediaLinks(),
                shouldShowError = false,
                errorMessage = 0,
                isContentEmpty = false,
                isLoadingMovies = false,
                isLoadingImages = false
            )
        }
        loadActorDetails()
    }


    override fun onMovieClick(movie: MediaItemUiState) {
        sendEvent(CastDetailsEffect.NavigateToMovie(movie.id))
    }

    override fun onShowMoreMovies() {
        sendEvent(
            CastDetailsEffect.NavigateToFullMovieList(
                actorId,
                uiState.value.actor?.name ?: ""
            )
        )
    }

    override fun onShowMoreGallery() {
        sendEvent(
            CastDetailsEffect.NavigateToFullGallery(
                actorId,
                uiState.value.actor?.name ?: ""
            )
        )
    }
}