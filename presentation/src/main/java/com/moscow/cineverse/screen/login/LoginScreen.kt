package com.moscow.cineverse.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.AppTextField
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun LoginScreen() {
    LoginScreenContent()
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
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
            value = "",
            onValueChange = {},
            placeholder = stringResource(com.moscow.cinverse.presentation.R.string.enter_your_email_or_username),
            leadingIcon = R.drawable.outline_user,
            leadingIconTint = Theme.colors.shade.tertiary,
            maxLines = 1,
            isError = true,
            errorMessage = "Usernames can only include letters and numbers"
        )
        AppTextField(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally),
            label = stringResource(com.moscow.cinverse.presentation.R.string.password),
            value = "",
            onValueChange = {},
            isPassword = true,
            maxLines = 1,
            placeholder = stringResource(com.moscow.cinverse.presentation.R.string.enter_your_password),
            leadingIcon = R.drawable.outline_lock,
            leadingIconTint = Theme.colors.shade.tertiary
        )
        MovieButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp),
            buttonText = stringResource(com.moscow.cinverse.presentation.R.string.login),
            textColor = Theme.colors.button.onPrimary,
            textStyle = Theme.textStyle.body.medium.medium,
            onClick = {},
            buttonColor = Theme.colors.button.primary,
        )
        MovieButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp),
            buttonText = stringResource(com.moscow.cinverse.presentation.R.string.join_as_guest),
            textColor = Theme.colors.button.onSecondary,
            textStyle = Theme.textStyle.body.medium.medium,
            onClick = {},
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
            onClick = {},
            buttonColor = Theme.colors.button.secondary,
        )
    }
}

@Preview()
@Composable()
fun LoginScreenPreview() {
    LoginScreen()
}