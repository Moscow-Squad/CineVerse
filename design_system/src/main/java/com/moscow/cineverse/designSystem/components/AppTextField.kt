package com.moscow.cineverse.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textStyle: TextStyle = LocalTextStyle.current,
    forgotPasswordClick : (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "label",
                modifier = Modifier
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.background.card),
            textStyle = textStyle,
            placeholder = placeholder?.let { { Text(it) } },
            singleLine = singleLine,
            maxLines = maxLines,
            isError = isError,
            enabled = enabled,
            leadingIcon ={
                Icon(
                    imageVector = if (isPassword) Icons.Outlined.Lock else Icons.Outlined.Person,
                    contentDescription = "leading icon"
                )
            },
            trailingIcon = {
                when {
                    isPassword -> {
                        val image = if (passwordVisible) Icons.Filled.AddCircle else Icons.Default.Call
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = "Toggle password visibility")
                        }
                    }
                    isError ->{
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    trailingIcon != null -> trailingIcon()
                }
            },
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
            )
        )

        if (isError && !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        if (isPassword ){
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Forgot Password?",
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        if (forgotPasswordClick != null) {
                            forgotPasswordClick()
                        }
                    }
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBasicAppTextField() {
    var text by remember { mutableStateOf("") }
    AppTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = "Enter your name",
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewPasswordAppTextField() {
    var password by remember { mutableStateOf("") }
    AppTextField(
        value = password,
        onValueChange = { password = it },
        placeholder = "Password",
        isPassword = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorAppTextField() {
    var email by remember { mutableStateOf("abc@") }
    AppTextField(
        value = email,
        onValueChange = { email = it },
        placeholder = "Email",
        isError = true,
        errorMessage = "Error Message",
    )
}
