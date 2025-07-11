package com.example.image_viewer.component

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import com.example.image_viewer.component.classfier.NSFWClassifier
import com.skydoves.cloudy.cloudy

@Composable
fun SafeImageViewer(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    blurRadius: Int = 300,
    placeholder: Painter? = null,
    error: Painter? = null,
    fallback: Painter? = error,
    onLoading: ((State.Loading) -> Unit)? = null,
    onSuccess: ((State.Success) -> Unit)? = null,
    onError: ((State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
) {

    val context = LocalContext.current
    val classifier = remember { NSFWClassifier(context) }

    var blurImage by remember { mutableStateOf(true) }

    Box(modifier = modifier) {
        AsyncImage(
            model = model,
            modifier = modifier.cloudy(radius = blurRadius, enabled = blurImage),
            onSuccess = { success ->
                val bitmapImage = success.result.image.toBitmap()
                blurImage = classifier.classifyImage(bitmapImage)
                if (onSuccess != null)
                    onSuccess(success)
            },
            contentDescription = contentDescription,
            placeholder = placeholder,
            error = error,
            fallback = fallback,
            onLoading = onLoading,
            onError = onError,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
            filterQuality = filterQuality,
        )
    }
}