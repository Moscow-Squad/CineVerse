package com.moscow.cineverse.designSystem.component.text

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.preview.CineVersePreviews
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun AppTextField(
    value: String,
    leadingIcon: Int,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    label: String? = null,
    leadingIconTint: Color = LocalContentColor.current,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    forgotPasswordClick: (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
        ) {
            if (label != null) {
                Text(
                    text = label,
                    style = Theme.textStyle.body.medium.regular,
                    color = Theme.colors.shade.secondary,
                    modifier = Modifier
                )
            }
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Theme.colors.background.card,
                    shape = RoundedCornerShape(Theme.radius.large)
                ),
            textStyle = Theme.textStyle.body.medium.medium,
            placeholder = placeholder?.let {
                {
                    Text(
                        text = it,
                        style = Theme.textStyle.body.medium.regular,
                        color = Theme.colors.shade.tertiary
                    )
                }
            },
            singleLine = singleLine,
            maxLines = maxLines,
            isError = isError,
            enabled = enabled,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(size = 20.dp),
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null,
                    tint = leadingIconTint
                )
            },
            trailingIcon = {
                when {
                    isPassword -> {
                        val image = if (passwordVisible) {
                            painterResource(id = R.drawable.outline_eye_opened)
                        } else {
                            painterResource(id = R.drawable.outline_eye_closed)
                        }
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible }
                        ) {
                            Icon(
                                painter = image,
                                contentDescription = null,
                                modifier = Modifier.size(size = 20.dp),
                                tint = Theme.colors.shade.secondary
                            )
                        }
                    }

                    isError -> {
                        Icon(
                            modifier = Modifier.size(size = 20.dp),
                            painter = painterResource(id = R.drawable.outline_danger_triangle),
                            contentDescription = null,
                            tint = Theme.colors.additional.primary.red
                        )
                    }

                    trailingIcon != null -> trailingIcon()
                }
            },
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            shape = RoundedCornerShape(size = Theme.radius.large),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Theme.colors.shade.primary,
                unfocusedTextColor = Theme.colors.shade.primary,
                focusedBorderColor = Theme.colors.brand.primary,
                unfocusedBorderColor = Theme.colors.stroke.primary,
                errorBorderColor = Theme.colors.additional.primary.red,
                errorTextColor = Theme.colors.shade.primary,
                cursorColor = Theme.colors.brand.primary,
                errorCursorColor = Color.Transparent,
            )
        )
        if (isError && !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                color = Theme.colors.additional.primary.red,
                style = Theme.textStyle.body.small.regular,
            )
        }
        if (isPassword) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.forgot_password),
                    textDecoration = TextDecoration.Underline,
                    style = Theme.textStyle.body.medium.regular,
                    color = Theme.colors.shade.secondary,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        forgotPasswordClick?.invoke()
                    }
                )
            }
        }
    }
}

@CineVersePreviews
@Composable
private fun PreviewBasicAppTextField() {
    CineVerseTheme {
        var text by remember { mutableStateOf("") }
        AppTextField(
            label = "label",
            value = text,
            onValueChange = { text = it },
            placeholder = "Enter your name",
            leadingIcon = R.drawable.outline_user,
            leadingIconTint = Theme.colors.shade.tertiary
        )
    }
}