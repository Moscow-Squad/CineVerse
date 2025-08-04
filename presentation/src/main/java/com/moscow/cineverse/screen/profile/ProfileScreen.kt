package com.moscow.cineverse.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.profile.component.ProfileChipItem
import com.moscow.cineverse.screen.profile.component.ProfileChips
import com.moscow.cineverse.screen.profile.component.Settings
import com.moscow.cineverse.screen.profile.component.UserInfo
import com.moscow.cinverse.presentation.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigateToLogout: (String) -> Unit,
    navigateToEditProfile: (String, String) -> Unit
) {

    LaunchedEffect(Unit) {
        profileViewModel.getAccountDetails(
            accountId = "22117857",
            sessionId = "bdaff8b9113d11df4ac7bdb9665e5800d15bb9f3"
        )
    }

    val state by profileViewModel.uiState.collectAsState()

    ProfileContent(
        modifier,
        profileViewModel,
        isDarkTheme = state.isDarkTheme,
        onThemeChange = profileViewModel::updateAppTheme,
        appLanguage = state.appLanguage,
        onLanguageChange = profileViewModel::updateAppLanguage
    )
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    listener: ProfileInteractionListener,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    appLanguage: String,
    onLanguageChange: (String) -> Unit,
) {

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
                name = "Shrouk Mohamed",
                username = "shrouk_mohamed16",
                onClick = { listener.onClickEditProfile() }

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
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange,
                appLanguage = appLanguage,
                onLanguageChange = onLanguageChange,
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

    }

}

