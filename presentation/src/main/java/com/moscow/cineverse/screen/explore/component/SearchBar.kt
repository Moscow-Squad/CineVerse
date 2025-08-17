package com.moscow.cineverse.screen.explore.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.utlis.noRibbleClick
import com.moscow.cinverse.presentation.R
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
    onFirstFocus: () -> Unit = {},
    focusState: MutableState<Boolean> = mutableStateOf(false)
) {
    var isFocused by focusState
    var blockRefocus by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

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
                painter = painterResource(R.drawable.outline_arrow_left),
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

        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { fs ->
                    if (!blockRefocus) {
                        if (fs.isFocused) onFirstFocus()
                        isFocused = fs.isFocused
                    }
                }
                .background(Theme.colors.background.card, RoundedCornerShape(Theme.radius.large))
                .border(
                    width = 1.dp,
                    color = Theme.colors.stroke.primary,
                    shape = RoundedCornerShape(Theme.radius.large)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                // Leading search icon
                Icon(
                    painter = painterResource(R.drawable.outline_search),
                    contentDescription = stringResource(R.string.search_icon),
                    tint = Theme.colors.shade.tertiary,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.size(8.dp)) // ✅ exactly 8.dp spacing

                // Text field
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = Theme.textStyle.body.medium.medium.copy(
                        color = Theme.colors.shade.primary,
                        fontSize = 14.sp
                    ),
                    singleLine = singleLine,
                    maxLines = maxLines,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    cursorBrush = SolidColor(Theme.colors.brand.primary),
                    modifier = Modifier.weight(1f)
                ) { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search),
                            style = Theme.textStyle.body.medium.regular,
                            color = Theme.colors.shade.tertiary,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }

                // Trailing icon
                if (isFocused && value.isNotEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.outline_x),
                        contentDescription = stringResource(R.string.search_icon),
                        tint = Theme.colors.shade.tertiary,
                        modifier = Modifier
                            .size(20.dp)
                            .noRibbleClick { onValueChange("") }
                    )
                } else {
                    trailingIcon()
                }
            }
        }
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
                painter = painterResource(R.drawable.outline_search),
                contentDescription = stringResource(R.string.search_icon)
            )
        },
        onCancelButtonClicked = { search = "" }
    )
}