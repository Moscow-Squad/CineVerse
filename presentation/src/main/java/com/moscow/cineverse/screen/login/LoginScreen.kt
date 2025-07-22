package com.moscow.cineverse.screen.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.AppTextField
import com.moscow.cineverse.designSystem.component.MessageInfoBox
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.component.login.SignupBrowser
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.navigation.routes.ExploreRoute
import com.moscow.cineverse.navigation.routes.LoginRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController = LocalNavController.current,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginScreenEvents.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }

                is LoginScreenEvents.NavigateToExplore -> {
                    val canGoBack = navController.previousBackStackEntry != null
                    if (canGoBack) {
                        navController.popBackStack()
                    } else {
                        navController.navigate(ExploreRoute) {
                            popUpTo(LoginRoute) { inclusive = true }
                        }
                    }
                }
            }
        }
    }

    LoginScreenContent(
        state = state,
        interactionListener = viewModel
    )
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    state: LoginScreenState,
    interactionListener: LoginInteractionListener
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Theme.colors.background.screen)
    ) {
        Image(
            painter = painterResource(R.drawable.cine_verse_logo),
            contentDescription = stringResource(
                com.moscow.cinverse.presentation.R.string.cine_verse_logo
            ),
            modifier = Modifier
                .padding(top = 64.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(com.moscow.cinverse.presentation.R.string.welcome_back_to_cineverse),
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.title.medium
        )
        AppTextField(
            modifier = Modifier
                .padding(top = 48.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally),
            label = stringResource(com.moscow.cinverse.presentation.R.string.email_or_username),
            value = state.username,
            onValueChange = interactionListener::onUsernameValueChanged,
            placeholder = stringResource(com.moscow.cinverse.presentation.R.string.enter_your_email_or_username),
            leadingIcon = R.drawable.outline_user,
            leadingIconTint = Theme.colors.shade.tertiary,
            maxLines = 1,
            isError = state.usernameError != null,
            errorMessage = state.usernameError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                autoCorrectEnabled = true
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        AppTextField(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally),
            label = stringResource(com.moscow.cinverse.presentation.R.string.password),
            value = state.password,
            onValueChange = interactionListener::onPasswordValueChanged,
            isPassword = true,
            maxLines = 1,
            placeholder = stringResource(com.moscow.cinverse.presentation.R.string.enter_your_password),
            leadingIcon = R.drawable.outline_lock,
            leadingIconTint = Theme.colors.shade.tertiary,
            isError = state.passwordError != null,
            errorMessage = state.passwordError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                autoCorrectEnabled = false
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
        MovieButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp),
            buttonText = stringResource(com.moscow.cinverse.presentation.R.string.login),
            textColor = Theme.colors.button.onPrimary,
            textStyle = Theme.textStyle.body.medium.medium,
            onClick = interactionListener::onClickLogin,
            buttonColor = Theme.colors.button.primary,
            isLoading = state.isLoading,
            enable = state.usernameError == null && state.passwordError == null
        )
        MovieButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp),
            buttonText = stringResource(com.moscow.cinverse.presentation.R.string.join_as_guest),
            textColor = Theme.colors.button.onSecondary,
            textStyle = Theme.textStyle.body.medium.medium,
            onClick = interactionListener::onClickJoinAsGuest,
            buttonColor = Theme.colors.button.secondary,
        )
        Spacer(modifier = Modifier.weight(1f))
        MovieButton(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.CenterHorizontally),
            buttonText = stringResource(com.moscow.cinverse.presentation.R.string.create_a_new_account),
            textColor = Theme.colors.button.onSecondary,
            textStyle = Theme.textStyle.body.medium.medium,
            textPadding = PaddingValues(horizontal = 16.dp),
            onClick = interactionListener::onClickCreateNewAccount,
            buttonColor = Theme.colors.button.secondary,
        )
        AnimatedVisibility(state.showSignUpBottomSheet) {
            SignUpBottomSheet(interactionListener)
        }
    }
    if (state.wantToSignup) {
        SignupBrowser(
            onExitWebView = {
                interactionListener.onDismissOrCancelSignUpBottomSheet()
                interactionListener.onExitSignupBrowser()
            }
        )
    }
}

@Composable
fun SignUpBottomSheet(interactionListener: LoginInteractionListener) {
    CineVerseBottomSheet(
        onClose = interactionListener::onDismissOrCancelSignUpBottomSheet,
        expanded = false,
        onDismissRequest = interactionListener::onDismissOrCancelSignUpBottomSheet,
        showCancelIcon = false,
    ) {
        MessageInfoBox(
            title = stringResource(com.moscow.cinverse.presentation.R.string.join_cineverse),
            description = stringResource(com.moscow.cinverse.presentation.R.string.let_s_get_you_set_up_we_ll_take_you_to_the_website_to_create_your_account),
            icon = painterResource(R.drawable.due_tone_link_minimalistic),
            showButtonsGroup = true,
            firstButtonText = stringResource(com.moscow.cinverse.presentation.R.string.cancel),
            onClickFirstButton = interactionListener::onDismissOrCancelSignUpBottomSheet,
            secondButtonText = stringResource(com.moscow.cinverse.presentation.R.string.go_to_website),
            onClickSecondButton = interactionListener::onClickGoToWebsite
        )
    }
}

@Preview()
@Composable()
fun LoginScreenPreview() {
    val nav = rememberNavController()
    LoginScreen(nav)
}