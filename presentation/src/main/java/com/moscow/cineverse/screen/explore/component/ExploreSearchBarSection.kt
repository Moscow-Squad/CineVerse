package com.moscow.cineverse.screen.explore.component

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.search.SearchBar
import com.moscow.cineverse.screen.explore.ExploreInteractionListener
import com.moscow.cineverse.screen.explore.ExploreScreenState

@Composable
fun ExploreSearchBarSection(
    uiState: ExploreScreenState,
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val focusState = remember { mutableStateOf(false) }

    BackHandler(enabled = uiState.showSuggestions) {
        interactionListener.onCancelButtonClicked()
        focusState.value = false
    }
    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 56.dp)
            .padding(horizontal = 16.dp),
        value = uiState.searchKeyWord,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onNext = { interactionListener.onKeyboardClick() },
            onSearch = {
                if (uiState.searchKeyWord.isBlank())
                    Toast.makeText(context, "Search can't be empty!", Toast.LENGTH_SHORT).show()
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
