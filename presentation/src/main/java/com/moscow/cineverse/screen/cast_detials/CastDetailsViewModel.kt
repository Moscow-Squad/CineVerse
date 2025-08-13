package com.moscow.cineverse.screen.cast_detials

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.screen.cast_detials.CastDetailsUiState.SocialMediaLinks
import com.moscow.cinverse.presentation.R
import com.moscow.domain.model.Actor
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
            action = { getActorDetailsUseCase.invoke(actorId) },
            onSuccess = ::onActorDetailsSuccess,
            onError = ::onActorDetailsError,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onActorDetailsSuccess(actor: Actor) {
        updateState {
            it.copy(
                actor = actor,
                isLoading = false,
                shouldShowError = false,
                errorMessage = 0,
                socialMediaLinks = SocialMediaLinks(
                    youtube = actor.socialMediaLinks.youtube.takeIf { !it.isNullOrBlank() },
                    facebook = actor.socialMediaLinks.facebook.takeIf { !it.isNullOrBlank() },
                    instagram = actor.socialMediaLinks.instagram.takeIf { !it.isNullOrBlank() },
                    twitter = actor.socialMediaLinks.twitter.takeIf { !it.isNullOrBlank() },
                    tiktok = actor.socialMediaLinks.tiktok.takeIf { !it.isNullOrBlank() }
                ),
                isContentEmpty = false
            )
        }

        loadGalleryImages()
        loadBestOfMovies()
    }

    private fun loadGalleryImages() {
        updateState { it.copy(isLoadingImages = true) }

        viewModelScope.launch {
            try {
                getActorGalleryUseCase.invoke(actorId).forEach { image ->
                    updateState {
                        it.copy(
                            images = it.images + image,
                            isLoadingImages = false
                        )
                    }
                }
            } catch (_: Exception) {
                updateState {
                    it.copy(
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
                updateState {
                    it.copy(
                        movies = movies,
                        isLoadingMovies = false
                    )
                }
            },
            onError = { e ->
                updateState {
                    it.copy(
                        isLoading = false,
                        shouldShowError = true,
                        errorMessage = e,
                        movies = emptyList(),
                        isLoadingMovies = false
                    )
                }
            },
            onStart = {
                updateState {
                    it.copy(isLoadingMovies = true)
                }
            },
        )
    }

    private fun onActorDetailsError(msg: Int) {
        updateState {
            it.copy(
                isLoading = false,
                shouldShowError = true,
                errorMessage = msg,
                isContentEmpty = false
            )
        }
    }

    private fun onLoading() {
        updateState {
            it.copy(
                isLoading = true,
                shouldShowError = false,
                errorMessage = 0
            )
        }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
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
        if (targetUrl.isNullOrBlank()) {
            updateState {
                it.copy(
                    shouldShowError = true,
                    errorMessage = R.string.link_not_available
                )
            }
            return
        }
        sendEvent(CastDetailsEffect.OpenSocialMedia(platform, targetUrl))
    }

    override fun onRefresh() {
        updateState {
            it.copy(
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

    override fun onMovieClick(movie: Movie) {
        sendEvent(CastDetailsEffect.NavigateToMovie(movie.id))
    }

    override fun onShowMoreMovies() {
        sendEvent(
            CastDetailsEffect.NavigateToFullMovieList(
                actorId,
                uiState.value.actor?.name.orEmpty()
            )
        )
    }

    override fun onBackPressed() {
        sendEvent(CastDetailsEffect.NavigateBack)
    }

    override fun onShowMoreGallery() {
        sendEvent(
            CastDetailsEffect.NavigateToFullGallery(
                actorId,
                uiState.value.actor?.name.orEmpty()
            )
        )
    }
}