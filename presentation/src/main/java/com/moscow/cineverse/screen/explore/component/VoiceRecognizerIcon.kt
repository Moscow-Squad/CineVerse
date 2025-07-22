package com.moscow.cineverse.screen.explore.component

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.example.design_system.R as DesignSystemR
import com.moscow.cinverse.presentation.R as PresentationR

@Composable
fun VoiceRecognitionIcon(
    onResult: (List<String>) -> Unit,
    onError: (() -> Unit),
    modifier: Modifier = Modifier,
    @StringRes prompt: Int = PresentationR.string.speak_to_search,
    maxResults: Int = 5,
    enabled: Boolean = true
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        when (activityResult.resultCode) {
            Activity.RESULT_OK -> {
                val results = activityResult.data
                    ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    ?: emptyList()
                onResult(results)
            }
            else -> onError()
        }
    }

    IconButton(
        onClick = {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(RecognizerIntent.EXTRA_PROMPT, prompt)
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, maxResults)
            }
            launcher.launch(intent)
        },
        modifier = modifier,
        enabled = enabled
    ) {
        Icon(
            painter = painterResource(DesignSystemR.drawable.outline_microphone),
            contentDescription = stringResource(PresentationR.string.microphone),
            tint = Theme.colors.shade.primary
        )
    }
}

@Preview
@Composable
private fun VoiceRecognitionIconPreview() {
    CineVerseTheme {
        VoiceRecognitionIcon(
            onResult = {},
            onError = {}
        )
    }
}