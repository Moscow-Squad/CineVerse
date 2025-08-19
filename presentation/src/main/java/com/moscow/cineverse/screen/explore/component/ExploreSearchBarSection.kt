package com.moscow.cineverse.screen.explore.component

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.screen.explore.ExploreInteractionListener
import com.moscow.cineverse.screen.explore.ExploreScreenState
import com.moscow.cinverse.presentation.R

@Composable
fun ExploreSearchBarSection(
    uiState: ExploreScreenState,
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true
) {
    val context = LocalContext.current
    val focusState = remember { mutableStateOf(false) }

    BackHandler(enabled = uiState.showSuggestions) {
        interactionListener.onCancelButtonClicked()
        focusState.value = false
    }

    // Animated height for collapse effect
    val height by animateDpAsState(
        targetValue = if (isVisible) 60.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = androidx.compose.animation.core.FastOutSlowInEasing
        ),
        label = "searchBarHeight"
    )

    // Animated alpha for smooth fade
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = androidx.compose.animation.core.FastOutSlowInEasing
        ),
        label = "searchBarAlpha"
    )

    Box(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(height)
            .alpha(alpha)
    ) {
        if (height > 0.dp) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = uiState.searchKeyWord,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onNext = { interactionListener.onKeyboardClick() },
                    onSearch = {
                        if (uiState.searchKeyWord.isBlank())
                            Toast.makeText(
                                context,
                                context.getString(R.string.search_can_t_be_empty_1),
                                Toast.LENGTH_SHORT
                            ).show()
                        else interactionListener.onSearchQuery()
                    }
                ),
                onValueChange = { interactionListener.onSearchValueChange(it) },
                onCancelButtonClicked = { interactionListener.onCancelButtonClicked() },
                onFirstFocus = { interactionListener.onSearchBarClickedOn() },
                trailingIcon = {
                    VoiceRecognitionIcon(
                        modifier = Modifier.size(20.dp),
                        onResult = { interactionListener.onSearchWordDetected(it) },
                        onError = {}
                    )
                },
                focusState = focusState
            )
        }
    }
}