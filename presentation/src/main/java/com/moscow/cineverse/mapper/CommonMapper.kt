package com.moscow.cineverse.mapper

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.moscow.cineverse.common_ui_state.DurationUiState
import com.moscow.cineverse.design_system.R
import kotlinx.datetime.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DecimalStyle
import java.util.Locale


@Composable
fun DurationUiState.formatDuration(): String {
    return when {
        hours > 0 && minutes > 0 -> stringResource(R.string.duration_hours_minutes, hours, minutes)
        hours > 0 -> stringResource(R.string.duration_hours_only, hours)
        minutes > 0 -> stringResource(R.string.duration_minutes_only, minutes)
        else -> ""
    }
}

fun LocalDate.formatDate(context: Context): String {
    return try {
        val appLocale = context.resources.configuration.locales[0]
        val javaLocalDate = java.time.LocalDate.of(this.year, this.monthNumber, this.dayOfMonth)
        val isArabicApp = appLocale.language == "ar"

        val formatter = if (isArabicApp) {
            val arabicLocale = Locale.Builder()
                .setLanguage("ar")
                .setRegion("SA")
                .build()
            val arabicDecimalStyle = DecimalStyle.of(arabicLocale)
            DateTimeFormatter.ofPattern("yyyy, MMM dd", appLocale)
                .withDecimalStyle(arabicDecimalStyle)
        } else {
            DateTimeFormatter.ofPattern("yyyy, MMM dd", appLocale)
        }
        javaLocalDate.format(formatter)
    } catch (_: Exception) {
        ""
    }
}