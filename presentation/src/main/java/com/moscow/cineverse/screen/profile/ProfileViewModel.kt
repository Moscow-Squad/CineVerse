package com.moscow.cineverse.screen.profile

import android.util.Log
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.domain.model.UserType
import com.moscow.domain.usecase.local.GetUserDetailsUseCase
import com.moscow.domain.usecase.local.RemoveUserDetailsUseCase
import com.moscow.domain.usecase.profile.GetAccountDetailsUseCase
import com.moscow.domain.usecase.profile.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val removeUserDetailsUseCase: RemoveUserDetailsUseCase,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase
) : BaseViewModel<ProfileUIState, ProfileScreenEffects>(ProfileUIState()),
    ProfileInteractionListener {

    init {
        getUserDetails()
    }

    private fun logout() {
        Log.d("ProfileViewModel", "Session ID: ${uiState.value.sessionId}")

        launchWithResult(
            action = { logoutUseCase(sessionId = uiState.value.sessionId) },
            onSuccess = ::onGetLogoutSuccess,
            onError = { onLogoutFailed() }
        )
    }

    private fun onGetLogoutSuccess(success: Boolean) {
        if (success) {
            removeUserDetails()
        }
        onCancelLogoutBottomSheet()
    }
    private fun onLogoutFailed(){
        onCancelLogoutBottomSheet()
        sendEvent(ProfileScreenEffects.onLogoutFailed)
    }

    fun removeUserDetails() {
        launchWithResult(
            action = { removeUserDetailsUseCase() },
            onSuccess = {sendEvent(ProfileScreenEffects.onLogoutSuccessfully)},
            onError = {}
        )
    }
    fun getUserDetails() {
        launchWithResult(
            action = { getUserDetailsUseCase() },
            onSuccess = ::onGetAccountDetailsSuccess,
            onError = {}
        )
    }

    private fun onGetAccountDetailsSuccess(userType: UserType) {
        when (userType) {
            is UserType.AuthenticatedUser -> {
                updateState {
                    it.copy(
                        name = userType.name,
                        username = userType.username,
                        image = userType.image,
                        sessionId = userType.sessionId

                    )
                }
            }

            is UserType.GuestUser -> {
                updateState {
                    it.copy(
                        isGuest = true
                    )
                }
            }
        }

    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true, errorMessage = null) }
    }

    override fun onShowEditProfileBottomSheet() {
        sendEvent(ProfileScreenEffects.showEditProfileBottomSheet)
    }

    override fun onShowLogoutBottomSheet() {
        updateState { it.copy(showLogoutBottomSheet = true) }
    }

    override fun onShowLanguageBottomSheet() {
        updateState { it.copy(showLanguageBottomSheet = true) }
    }

    override fun onClickEditProfile() {
        TODO("Not yet implemented")
    }

    override fun onClickLogout() {
        logout()
    }

    override fun onSelectedLanguage(language: String) {
        TODO("Not yet implemented")
    }

    override fun onCancelLanguageBottomSheet() {
        updateState { it.copy(showLanguageBottomSheet = false) }
    }

    override fun onCancelEditProfileBottomSheet() {
        updateState { it.copy(showEditProfileBottomSheet = false) }
    }

    override fun onCancelLogoutBottomSheet() {
        updateState { it.copy(showLogoutBottomSheet = false) }
    }

    override fun onClickMyRatings() {
        sendEvent(ProfileScreenEffects.navigateToMyRating)
    }

    override fun onClickMyCollections() {
        sendEvent(ProfileScreenEffects.navigateToMyCollections)

    }

    override fun onClickHistory() {
        sendEvent(ProfileScreenEffects.navigateToHistory)

    }


}