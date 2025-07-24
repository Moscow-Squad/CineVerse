@file:Suppress("DEPRECATION")

package com.moscow.cineverse.designSystem.component.login

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.moscow.cineverse.designSystem.component.MovieScaffold

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SignupBrowser(
    onExitWebView: () -> Unit
) {
    val url = "https://www.themoviedb.org/signup"
    val state = rememberWebViewState(url)

    val webViewHolder = remember { WebViewHolder() }

    BackHandler {
        val canGoBack = webViewHolder.webView?.canGoBack() == true
        if (canGoBack) {
            webViewHolder.webView?.goBack()
        } else {
            onExitWebView()
        }
    }

    MovieScaffold() {
        WebView(
            state = state,
            modifier = Modifier.fillMaxSize(),
            onCreated = {
                it.settings.javaScriptEnabled = true
                webViewHolder.webView = it
            },
            client = remember { AccompanistWebViewClient() }
        )
    }
}

// Helper class to store WebView reference
class WebViewHolder {
    var webView: WebView? = null
}