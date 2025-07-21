@file:Suppress("DEPRECATION")

package com.moscow.cineverse.designSystem.component.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SignupBrowser() {
    val url = "https://www.themoviedb.org/signup"
    val state = rememberWebViewState(url)
    WebView(
        state = state,
        modifier = Modifier.fillMaxSize(),
        onCreated = { it.settings.javaScriptEnabled = true },
        client = remember { AccompanistWebViewClient() }
    )
}