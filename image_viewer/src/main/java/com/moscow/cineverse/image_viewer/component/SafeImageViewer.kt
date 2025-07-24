package com.moscow.cineverse.image_viewer.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.Bitmap
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import com.moscow.cineverse.image_viewer.classfier.HybridImageClassifier
import com.skydoves.cloudy.cloudy

@Composable
fun SafeImageViewer(
    imageUrl: String,
    modifier: Modifier = Modifier,
    blurRadius: Int = 25,
    isBlurEnabled: Boolean = true,
    placeholderContent: @Composable () -> Unit = {},
    errorContent: @Composable () -> Unit = {},
    onSuccess: (() -> Unit)? = null,
    onError: (() -> Unit)? = null,
    onBlurContent: @Composable () -> Unit = {},
) {
    val context = LocalContext.current
    val classifier = remember { HybridImageClassifier(context) }

    var bitmapToDisplay by remember { mutableStateOf<Bitmap?>(null) }
    var isHaram by rememberSaveable { mutableStateOf(isBlurEnabled) }
    var requestState by rememberSaveable { mutableStateOf(RequestState.LOADING) }

    LaunchedEffect(imageUrl) {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context).data(imageUrl).allowHardware(false).build()

        val result = runCatching { loader.execute(request) }
        result.onSuccess { success ->
            val bitmap = runCatching { success.image?.toBitmap() }.getOrNull()
            if (bitmap != null) {
                if (isBlurEnabled) isHaram = classifier.classifyImage(bitmap)
                bitmapToDisplay = bitmap
                requestState = RequestState.SUCCESS
                onSuccess?.invoke()
            } else {
                requestState = RequestState.ERROR
                onError?.invoke()
            }
        }.onFailure {
            requestState = RequestState.ERROR
            onError?.invoke()
        }
    }

    Box(modifier = modifier) {
        when (requestState) {
            RequestState.LOADING -> {
                placeholderContent()
            }

            RequestState.SUCCESS -> {
                bitmapToDisplay?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .cloudy(radius = blurRadius, enabled = isHaram),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            RequestState.ERROR -> {
                errorContent()
            }
        }
        if (requestState == RequestState.SUCCESS && isHaram && isBlurEnabled) {
            onBlurContent()
        }
    }
}

enum class RequestState {
    LOADING, SUCCESS, ERROR
}
