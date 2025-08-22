package com.moscow.cineverse.mapper

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.moscow.cineverse.common_ui_state.DurationUiState
import com.moscow.cineverse.design_system.R
import kotlinx.datetime.LocalDate
import java.text.NumberFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import java.time.format.DateTimeFormatter
import java.time.format.DecimalStyle


@Composable
fun DurationUiState.formatDuration(): String {
    return when {
        hours > 0 && minutes > 0 -> stringResource(R.string.duration_hours_minutes, hours, minutes)
        hours > 0 -> stringResource(R.string.duration_hours_only, hours)
        minutes > 0 -> stringResource(R.string.duration_minutes_only, minutes)
        else -> ""
    }
}

fun Float.formatRating(context: Context, decimalPlaces: Int = 1): String {
    return try {
        val appLocale = context.resources.configuration.locales[0]
        val isArabicApp = appLocale.language == "ar"

        val formatter = if (isArabicApp) {
            val arabicLocale = Locale.Builder()
                .setLanguage("ar")
                .setRegion("SA")
                .build()
            val arabicDecimalStyle = DecimalStyle.of(arabicLocale)
            (NumberFormat.getNumberInstance(arabicLocale) as DecimalFormat).apply {
                minimumFractionDigits = decimalPlaces
                maximumFractionDigits = decimalPlaces
                decimalFormatSymbols = DecimalFormatSymbols(arabicLocale).apply {
                    decimalSeparator = arabicDecimalStyle.decimalSeparator
                    zeroDigit = arabicDecimalStyle.zeroDigit
                }
            }
        } else {
            NumberFormat.getNumberInstance(appLocale).apply {
                minimumFractionDigits = decimalPlaces
                maximumFractionDigits = decimalPlaces
            }
        }
        formatter.format(this)
    } catch (e: Exception) {
        this.toString()
    }
}

fun Int.formatInt(context: Context): String {
    return try {
        val appLocale = context.resources.configuration.locales[0]
        val isArabicApp = appLocale.language == "ar"

        val formatter = if (isArabicApp) {
            val arabicLocale = Locale.Builder()
                .setLanguage("ar")
                .setRegion("SA")
                .build()
            (NumberFormat.getNumberInstance(arabicLocale) as DecimalFormat).apply {
                minimumFractionDigits = 0
                maximumFractionDigits = 0
                isGroupingUsed = false
                decimalFormatSymbols = DecimalFormatSymbols(arabicLocale).apply {
                    zeroDigit = DecimalStyle.of(arabicLocale).zeroDigit
                }
            }
        } else {
            NumberFormat.getNumberInstance(appLocale).apply {
                minimumFractionDigits = 0
                maximumFractionDigits = 0
                isGroupingUsed = false
            }
        }
        formatter.format(this)
    } catch (e: Exception) {
        this.toString()
    }
}

fun String.formatSeasonNumber(context: Context): String {
    return try {
        val regex = """\d+""".toRegex()
        val match = regex.find(this)
        if (match != null) {
            val number = match.value.toInt()
            val formattedNumber = number.formatInt(context)
            this.replace(match.value, formattedNumber)
        } else {
            this
        }
    } catch (e: Exception) {
        this
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