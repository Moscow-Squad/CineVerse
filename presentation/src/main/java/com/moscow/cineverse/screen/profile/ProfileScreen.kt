package com.moscow.cineverse.screen.profile

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.profile.component.ProfileChipItem
import com.moscow.cineverse.screen.profile.component.ProfileChips
import com.moscow.cineverse.screen.profile.component.Settings
import com.moscow.cineverse.screen.profile.component.UserInfo
import com.moscow.cinverse.presentation.R
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.component.message_info.MessageInfoBox

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToEditProfile: (String, String) -> Unit,
    navigateToMyRatings: () -> Unit,
    navigateToMyCollections: () -> Unit,
    navigateToMyHistory: () -> Unit,
) {
    val state by profileViewModel.uiState.collectAsStateWithLifecycle()

    /*LaunchedEffect(profileViewModel) {
        profileViewModel.uiEffect.collect { effect ->
            ProfileScreenEffectHandler.handleEffect(
               effect,

            )
        }
    }*/
    ProfileContent(
        modifier,
        state,
        profileViewModel
    )
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    uiState: ProfileUIState,
    listener: ProfileInteractionListener
) {
    Log.d("TAG", "ProfileContent: $uiState")
/*
    when {


        uiState.isLoading -> {
            MovieCircularProgressBar(modifier = modifier)
        }


        !uiState.errorMessage.isNullOrBlank() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen),
                contentAlignment = Alignment.Center
            ) {
                NoInternetScreen(onRetry = {})
            }
        }

        else -> {*/
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen),
                contentPadding = PaddingValues(16.dp)

            ) {

                item {
                    MovieAppBar(
                        showBackButton = false,
                        modifier = Modifier.padding(vertical = 17.dp),
                        title = stringResource(R.string.my_profile)
                    )
                }

                item {
                    UserInfo(
                        name = if (!uiState.name.isNullOrBlank()) uiState.name else uiState.username.orEmpty(),
                        username = uiState.username.orEmpty(),
                        userImage = if (uiState.image.isNullOrEmpty()) null else rememberAsyncImagePainter(
                            model = uiState.image
                        ),
                        isGuest = uiState.isGuest,
                        onClick = { listener.onShowEditProfileBottomSheet() }

                    )
                }

                item {
                    ProfileChips(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 24.dp),
                        items = listOf(
                            ProfileChipItem(
                                R.string.history,
                                R.drawable.due_tone_history,
                                {}
                            ),
                            ProfileChipItem(
                                R.string.my_collections,
                                R.drawable.due_tone_video_library,
                                {}
                            ),
                            ProfileChipItem(
                                R.string.my_ratings,
                                R.drawable.due_tone_star,
                                {}
                            )
                        )
                    )
                }

                item {
                    MovieText(
                        text = stringResource(R.string.settings),
                        color = Theme.colors.shade.primary,
                        style = Theme.textStyle.title.medium

                    )
                    Settings(
                        modifier = Modifier.padding(top = 12.dp, bottom = 24.dp),
                        isGuest = false,
                        onLogoutClick = {listener.onShowLogoutBottomSheet()},
                        onLanguageClick = {}
                    )
                }

                item {
                    MovieText(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Version 1.0",
                        color = Theme.colors.shade.tertiary,
                        style = Theme.textStyle.body.small.regular,
                        textAlign = TextAlign.Center

                    )
                }

                item {

                    AnimatedVisibility(uiState.showLogoutBottomSheet) {
                        CineVerseBottomSheet(
                            onClose = {listener.onCancelLogoutBottomSheet()},
                            onDismissRequest = {listener.onCancelLogoutBottomSheet()},
                            showCancelIcon = true,
                        ) {
                            MessageInfoBox(
                                title = stringResource(R.string.are_you_sure_you_want_to_logout),
                                description = stringResource(R.string.you_can_always_sign_back_in_with_your_account),
                                icon = painterResource(Theme.icons.dueTone.logout),
                                showButtonsGroup = true,
                                firstButtonText = stringResource(R.string.cancel),
                                onClickFirstButton = {listener.onCancelLogoutBottomSheet()},
                                secondButtonText = stringResource(R.string.logout),
                                onClickSecondButton = {listener.onClickLogout() },
                                modifier = Modifier.padding(bottom = 16.dp),
                                iconColor = Theme.colors.additional.primary.red,
                               secondButtonBackground = Theme.colors.additional.primary.red,

                            )

                        }

                    }
                }


    }

}

