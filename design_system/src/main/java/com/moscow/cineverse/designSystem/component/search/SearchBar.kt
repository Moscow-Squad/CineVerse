package com.moscow.cineverse.designSystem.component.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.designSystem.utils.noRibbleClick
import kotlinx.coroutines.delay

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
    onCancelButtonClicked: () -> Unit = {},
    onFirstFocus: () -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
    var blockRefocus by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val paddingStart by animateDpAsState(
        targetValue = if (isFocused || value.isNotEmpty()) 20.dp else 0.dp,
        animationSpec = tween(300)
    )

    LaunchedEffect(blockRefocus) {
        if (blockRefocus) {
            delay(500)
            blockRefocus = false
        }
    }

    LaunchedEffect(isFocused) {
        if (!isFocused) {
            focusManager.clearFocus(force = true)
            focusRequester.freeFocus()
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        AnimatedVisibility(
            visible = isFocused || value.isNotEmpty(),
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(300)),
            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(300))
        ) {
            Icon(
                painter = painterResource(Theme.icons.outline.arrowLeft),
                contentDescription = stringResource(R.string.search_icon),
                tint = Theme.colors.shade.primary,
                modifier = Modifier
                    .size(20.dp)
                    .noRibbleClick(
                        onClick = {
                            focusManager.clearFocus(force = true)
                            focusRequester.freeFocus()
                            isFocused = false
                            blockRefocus = true
                            onCancelButtonClicked()
                        },
                    )
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(start = paddingStart)
                .weight(1f)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (!blockRefocus) {
                        if (focusState.isFocused) {
                            onFirstFocus()
                        }
                        isFocused = focusState.isFocused
                    }
                }
                .background(Theme.colors.background.card, RoundedCornerShape(Theme.radius.large)),
            textStyle = Theme.textStyle.body.medium.medium.copy(color = Theme.colors.shade.primary),
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
                    Icon(
                        painter = painterResource(Theme.icons.outline.xClose),
                        contentDescription = stringResource(R.string.search_icon),
                        tint = Theme.colors.shade.tertiary,
                        modifier = Modifier
                            .size(20.dp)
                            .noRibbleClick{
                                onValueChange("")
                                onCancelButtonClicked()
                            }
                    )
                } else {
                    trailingIcon()
                }
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions (
                onSearch = {
                    keyboardActions.onSearch?.let { it() }
                    isFocused = false
                },
                onDone = {
                    keyboardActions.onDone?.let { it() }
                    isFocused = false
                }
            ),
            shape = RoundedCornerShape(Theme.radius.large),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Theme.colors.shade.primary,
                focusedBorderColor = Theme.colors.stroke.primary,
                unfocusedBorderColor = Theme.colors.stroke.primary,
                cursorColor = Theme.colors.brand.primary,
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