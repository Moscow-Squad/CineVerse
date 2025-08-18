package com.moscow.cineverse.image_viewer.component

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import com.moscow.cineverse.image_viewer.classfier.HybridImageClassifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private data class CachedImage(val bitmap: Bitmap, val isNsfw: Boolean)

private val imageCache = mutableMapOf<String, CachedImage>()

@Composable
fun SafeImageViewer(
    imageUrl: String,
    modifier: Modifier = Modifier,
    blurRadius: Int = 20,
    isBlurEnabled: String,
    placeholderContent: @Composable () -> Unit = {},
    errorContent: @Composable () -> Unit = {},
    onSuccess: (() -> Unit)? = null,
    onError: (() -> Unit)? = null,
    onBlurContent: @Composable () -> Unit = {},
) {
    val context = LocalContext.current
    val blur = rememberSaveable {
        when (isBlurEnabled) {
            "high", "medium" -> true
            else -> false
        }
    }
    val threshold = rememberSaveable {
        when (isBlurEnabled) {
            "high" -> 0.5f
            "medium" -> 0.2f
            else -> 0.2f
        }
    }

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizeBytes(50 * 1024 * 1024)
                    .build()
            }
            .build()
    }

    val classifier = remember(blur) {
        if (blur) HybridImageClassifier(context, threshold) else null
    }

    var bitmapToDisplay by rememberSaveable { mutableStateOf<Bitmap?>(null) }
    var isNsfw by rememberSaveable(imageUrl) { mutableStateOf(false) }
    var requestState by rememberSaveable(imageUrl) { mutableStateOf(RequestState.LOADING) }

    imageCache[imageUrl]?.let { cached ->
        bitmapToDisplay = cached.bitmap
        isNsfw = cached.isNsfw
        requestState = RequestState.SUCCESS
        onSuccess?.invoke()
    }

    val imageRequest = ImageRequest.Builder(context)
        .allowHardware(false)
        .data(imageUrl)
        .allowHardware(false)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()

    LaunchedEffect(imageUrl) {
        if (imageUrl.isEmpty()) {
            requestState = RequestState.ERROR
            onError?.invoke()
            return@LaunchedEffect
        }

        imageCache[imageUrl]?.let { cached ->
            bitmapToDisplay = cached.bitmap
            isNsfw = cached.isNsfw
            requestState = RequestState.SUCCESS
            onSuccess?.invoke()
            return@LaunchedEffect
        }

        requestState = RequestState.LOADING

        try {
            withContext(Dispatchers.IO) {
                val bitmap = imageLoader.execute(imageRequest).image?.toBitmap()
                if (bitmap != null) {
                    val shouldBlur = if (blur && classifier != null) {
                        withContext(Dispatchers.Default) {
                            classifier.classifyImage(bitmap)
                        }
                    } else false

                    imageCache[imageUrl] = CachedImage(bitmap, shouldBlur)
                    bitmapToDisplay = bitmap
                    isNsfw = shouldBlur
                    requestState = RequestState.SUCCESS
                    onSuccess?.invoke()
                } else {
                    requestState = RequestState.ERROR
                    onError?.invoke()
                }
            }

        } catch (_: Exception) {
            requestState = RequestState.ERROR
            onError?.invoke()
        }
    }

    val showBlur = remember(requestState, isNsfw, isBlurEnabled) {
        requestState == RequestState.SUCCESS && isNsfw && blur
    }

    Box(modifier = modifier) {
        when (requestState) {
            RequestState.LOADING -> placeholderContent()
            RequestState.SUCCESS -> {
                bitmapToDisplay?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .then(
                                if (showBlur && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    Modifier.blur(blurRadius.dp)
                                } else {
                                    Modifier
                                }
                            ),
                        contentScale = ContentScale.Crop,
                    )

                    if (showBlur) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            // Modern devices: Light overlay since native blur is strong
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(
                                        color = Color(1212123).copy(alpha = 0.24f)
                                    )
                            )
                        } else {
                            // Older devices: Multiple strong overlays for maximum coverage
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color.Black.copy(alpha = 0.9f))
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color.Gray.copy(alpha = 0.5f))
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color.Black.copy(alpha = 0.3f))
                            )
                        }
                        onBlurContent()
                    }
                }
            }

            RequestState.ERROR -> errorContent()
        }
    }
}

enum class RequestState {
    LOADING, SUCCESS, ERROR
}