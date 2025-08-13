package com.moscow.cineverse.screen.profile


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.profile.component.ContentPreferencesBottomSheet
import com.moscow.cineverse.screen.profile.component.EditProfileBottomSheet
import com.moscow.cineverse.screen.profile.component.LanguageBottomSheet
import com.moscow.cineverse.screen.profile.component.LogoutBottomSheet
import com.moscow.cineverse.screen.profile.component.ProfileChipItem
import com.moscow.cineverse.screen.profile.component.ProfileChips
import com.moscow.cineverse.screen.profile.component.Settings
import com.moscow.cineverse.screen.profile.component.UserInfo
import com.moscow.cinverse.presentation.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToWebSite: (String) -> Unit,
    navigateToMyRatings: () -> Unit,
    navigateToMyCollections: () -> Unit,
    navigateToMyHistory: () -> Unit,
) {
    val state by profileViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(profileViewModel) {
        profileViewModel.getUserDetails()
        profileViewModel.uiEffect.collect { effect ->
            ProfileScreenEffectHandler.handleEffect(
                effect,
                navigateToLogin,
                {},
                navigateToMyRatings,
                navigateToMyCollections,
                navigateToMyHistory,
                navigateToWebSite

            )
        }
    }
    ProfileContent(
        modifier,
        state,
        listener = profileViewModel,
        onThemeChange = profileViewModel::updateAppTheme,
    )
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    uiState: ProfileUIState,
    listener: ProfileInteractionListener,
    onThemeChange: (Boolean) -> Unit,
) {
    val context: Context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
    ) {

        item {
            MovieAppBar(
                showBackButton = false,
                modifier = Modifier.padding(vertical = 17.dp, horizontal = 16.dp),
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
                onClick = { listener.onShowEditProfileBottomSheet() },
                modifier = Modifier.padding(horizontal = 16.dp)

            )
        }

        item {
            ProfileChips(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 24.dp),
                items = listOf(
                    ProfileChipItem(
                        R.string.history, R.drawable.due_tone_history
                    ) {
                        if (uiState.isGuest)
                            listener.onShowEditProfileBottomSheet()
                        else
                            listener.onClickHistory()
                    },
                    ProfileChipItem(
                        R.string.my_collections, R.drawable.due_tone_video_library
                    ) {
                        if (uiState.isGuest)
                            listener.onShowEditProfileBottomSheet()
                        else
                            listener.onClickMyCollections()
                    },
                    ProfileChipItem(
                        R.string.my_ratings, R.drawable.due_tone_star
                    ) {
                        if (uiState.isGuest)
                            listener.onShowEditProfileBottomSheet()
                        else
                            listener.onClickMyRatings()
                    }
                )
            )
        }

        item {
            MovieText(
                text = stringResource(R.string.settings),
                color = Theme.colors.shade.primary,
                style = Theme.textStyle.title.medium,
                modifier = Modifier.padding(horizontal = 16.dp)

            )
            Settings(
                modifier = Modifier.padding(
                    top = 12.dp,
                    bottom = 24.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                isGuest = uiState.isGuest,
                isDarkTheme = uiState.isDarkTheme,
                onThemeChange = onThemeChange,
                onLanguageClick = { listener.onShowLanguageBottomSheet() },
                onLogoutClick = { listener.onShowLogoutBottomSheet() },
                onPreferenceClick = { listener.onShowPreferencesBottomSheet() }

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
            LogoutBottomSheet(
                visible = uiState.showLogoutBottomSheet,
                onDismiss = { listener.onCancelLogoutBottomSheet() },
                onLogoutClick = { listener.onClickLogout() })
        }
        item {
            LanguageBottomSheet(
                visible = uiState.showLanguageBottomSheet,
                selectedLanguage = uiState.currentLanguage,
                onDismiss = { listener.onCancelLanguageBottomSheet() },
                switchLanguage = { language ->
                    listener.onSelectedLanguage(language)
                }
            )
        }

        item {
            EditProfileBottomSheet(
                visible = uiState.showEditProfileBottomSheet,
                isGuest = uiState.isGuest,
                onDismiss = { listener.onCancelEditProfileBottomSheet() },
                onLoginClick = { listener.onClickLogin() },
                onEditProfile = { listener.onClickEditProfile() }
            )
        }
        item {
            ContentPreferencesBottomSheet(
                visible = uiState.showPreferencesBottomSheet,
                onDismiss = { listener.onCancelPreferencesBottomSheet() },
                selectedPreference = uiState.selectedPreference,
                onClickPreference = { enable -> listener.onSelectedPreference(enable = enable) }
            )
        }
    }

}
