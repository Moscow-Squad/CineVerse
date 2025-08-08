package com.moscow.cineverse.screen.login

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.moscow.cineverse.designSystem.component.MovieScaffold

@Composable
fun WebViewBrowser(
   url: String,
    onExitWebView: () -> Unit
) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    BackHandler {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            onExitWebView()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            webView.stopLoading()
            webView.destroy()
        }
    }

    MovieScaffold {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                webView.apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            },
            update = {
                if (it.url != url) {
                    it.loadUrl(url)
                }
            }
        )
    }
}