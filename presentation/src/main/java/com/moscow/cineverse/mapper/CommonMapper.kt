package com.moscow.cineverse.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.moscow.cineverse.common_ui_state.DurationUiState
import com.moscow.cineverse.design_system.R
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
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

fun LocalDate.formatDate(): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy, MMM dd", Locale.getDefault())
        val dateString = this.toJavaLocalDate().format(formatter)
        dateString
    } catch (_: Exception) {
        ""
    }
}