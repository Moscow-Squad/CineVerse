package com.moscow.cineverse.designSystem.component.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onCancelButtonClicked: () -> Unit = {}
) {
    var hasFocusedOnce by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused && !hasFocusedOnce) hasFocusedOnce = true

                    isFocused = focusState.isFocused
                }
                .background(Theme.colors.background.card, RoundedCornerShape(Theme.radius.large)),
            textStyle = Theme.textStyle.body.medium.medium,
            placeholder = {
                Text(
                    text = "Search...",
                    style = Theme.textStyle.body.medium.regular,
                    color = Theme.colors.shade.tertiary,
                    fontSize = 14.sp
                )
            },
            singleLine = singleLine,
            maxLines = maxLines,
            leadingIcon = {
                Icon(
                    painter = painterResource(Theme.icons.outline.search),
                    contentDescription = stringResource(R.string.search_icon),
                    tint = Theme.colors.shade.tertiary,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (isFocused || value.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            focusManager.clearFocus(force = true)
                            onCancelButtonClicked()
                            hasFocusedOnce = false
                            isFocused = false
                        }
                    ) {
                        Icon(
                            painter = painterResource(Theme.icons.outline.xClose),
                            contentDescription = stringResource(R.string.search_icon),
                            tint = Theme.colors.shade.tertiary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    IconButton(onClick = { }) {
                        trailingIcon()
                    }
                }
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            shape = RoundedCornerShape(Theme.radius.large),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Theme.colors.shade.primary,
                focusedBorderColor = Theme.colors.brand.primary,
                unfocusedBorderColor = Theme.colors.stroke.primary,
                errorBorderColor = Theme.colors.additional.primary.red,
                errorTextColor = Theme.colors.shade.primary,
                cursorColor = Theme.colors.brand.primary,
                errorCursorColor = Color.Transparent,
            )
        )
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    var search by remember { mutableStateOf("") }
    SearchBar(
        value = search,
        onValueChange = { search = it },
        trailingIcon = {
            Icon(
                painter = painterResource(Theme.icons.outline.search),
                contentDescription = stringResource(R.string.search_icon)
            )
        },
        onCancelButtonClicked = { search = "" }
    )
}