package com.moscow.cineverse.screen.login

import android.content.Context
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.moscow.cineverse.designSystem.component.AppTextField
import com.moscow.cineverse.designSystem.component.MessageInfoBox
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.component.login.WebViewBrowser
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { event ->
            when (event) {
                is LoginScreenEvents.ShowError -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_LONG).show()
                }

                is LoginScreenEvents.NavigateTo -> {
                    navigateToHome()

                }
            }
        }
    }

    LoginScreenContent(
        state = state,
        interactionListener = viewModel,
        context = context
    )
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    state: LoginScreenState,
    interactionListener: LoginInteractionListener,
    context: Context
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
            label = stringResource(com.moscow.cinverse.presentation.R.string.username),
            value = state.username,
            onValueChange = interactionListener::onUsernameValueChanged,
            placeholder = stringResource(com.moscow.cinverse.presentation.R.string.enter_your_username),
            leadingIcon = R.drawable.outline_user,
            leadingIconTint = Theme.colors.shade.tertiary,
            maxLines = 1,
            isError = state.usernameError != null,
            errorMessage = state.usernameError?.asString(context = context),
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
            errorMessage = state.passwordError?.asString(context),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                autoCorrectEnabled = false
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            forgotPasswordClick = interactionListener::onClickForgetPassword
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
            enable = (state.username.isNotBlank() && state.password.length >= 4) && (state.usernameError == null && state.passwordError == null)
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
            isLoading = state.isJoinAsGuest
        )
        Spacer(modifier = Modifier.weight(1f))
        MovieButton(
            modifier = Modifier
                .padding(vertical = 24.dp)
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
    AnimatedVisibility (state.showWebView) {
        WebViewBrowser(
            url = state.urlWebView,
            onExitWebView = {
                interactionListener.onExitWebViewBrowser()
            }
        )
    }
}

@Composable
fun SignUpBottomSheet(interactionListener: LoginInteractionListener) {
    CineVerseBottomSheet(
        onClose = interactionListener::onDismissOrCancelSignUpBottomSheet,
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
    CineVerseTheme {
        LoginScreen(navigateToHome = {})
    }
}