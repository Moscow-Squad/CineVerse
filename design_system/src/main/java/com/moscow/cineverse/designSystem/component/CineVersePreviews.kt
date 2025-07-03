package com.moscow.cineverse.designSystem.component

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
@Preview(
    showBackground = true,
    locale = "en",
    name = "EnglishLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    showBackground = false,
    locale = "ar",
    name = "ArabicLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    showBackground = true,
    locale = "en",
    name = "EnglishDark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    showBackground = true,
    locale = "ar",
    name = "ArabicDark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class CineVersePreviews