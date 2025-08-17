package com.moscow.cineverse.designSystem.component.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
    leadingIconTint: Color = Color.Unspecified,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    forgotPasswordClick: (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val shape = RoundedCornerShape(Theme.radius.large)
    val borderColor = when {
        isError -> Theme.colors.additional.primary.red
        isFocused -> Theme.colors.brand.primary
        else -> Theme.colors.stroke.primary
    }

    val shadeSecondaryColor = Theme.colors.shade.secondary
    val additionalPrimaryRedColor = Theme.colors.additional.primary.red

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        if (label != null) {
            BasicText(
                text = label,
                style = Theme.textStyle.body.medium.regular,
                color = { shadeSecondaryColor }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(Theme.colors.background.card, shape)
                .border(width = 1.dp, color = borderColor, shape = shape)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                maxLines = maxLines,
                enabled = enabled,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = if (isPassword && !passwordVisible) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                textStyle = Theme.textStyle.body.medium.medium.copy(color = Theme.colors.shade.primary),
                cursorBrush = SolidColor(Theme.colors.brand.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocused = it.isFocused },
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = leadingIcon),
                            contentDescription = null,
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                                leadingIconTint
                            ),
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Box(modifier = Modifier.weight(1f)) {
                            if (value.isEmpty() && !placeholder.isNullOrEmpty()) {
                                BasicText(
                                    text = placeholder,
                                    style = Theme.textStyle.body.medium.regular.copy(
                                        color = Theme.colors.shade.tertiary
                                    )
                                )
                            }
                            innerTextField()
                        }

                        when {
                            isPassword -> {
                                val image = if (passwordVisible) {
                                    painterResource(id = R.drawable.outline_eye_opened)
                                } else {
                                    painterResource(id = R.drawable.outline_eye_closed)
                                }
                                Image(
                                    painter = image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) { passwordVisible = !passwordVisible },
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                                        Theme.colors.shade.secondary
                                    )
                                )
                            }

                            isError -> {
                                Image(
                                    painter = painterResource(id = R.drawable.outline_danger_triangle),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                                        Theme.colors.additional.primary.red
                                    ),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            trailingIcon != null -> trailingIcon()
                        }
                    }
                }
            )
        }

        if (isError && !errorMessage.isNullOrEmpty()) {
            BasicText(
                text = errorMessage,
                style = Theme.textStyle.body.small.regular,
                color = { additionalPrimaryRedColor }
            )
        }

        if (isPassword) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicText(
                    text = stringResource(id = R.string.forgot_password),
                    style = Theme.textStyle.body.medium.regular.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = { shadeSecondaryColor },
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