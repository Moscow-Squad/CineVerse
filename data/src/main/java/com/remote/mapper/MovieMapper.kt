package com.remote.mapper

import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.remote.dto.MovieDetailsDto
import com.remote.dto.MovieDto
import com.remote.dto.SeriesDetailsDto
import com.remote.dto.SeriesDto
import java.time.LocalDate

class MovieMapper {
    fun mapToMovie(dto: MovieDto, details: MovieDetailsDto): Movie = Movie(
        id = dto.id,
        title = dto.title,
        posterUrl = "https://image.tmdb.org/t/p/w500${dto.posterPath}",
        voteAverage = dto.voteAverage,
        genres = details.genres.joinToString(", ") { it.name },
        duration = formatDuration(details.runtime),
        releaseDate = formatReleaseDate(dto.releaseDate)
    )

    fun mapToSeries(dto: SeriesDto, details: SeriesDetailsDto): Series = Series(
        id = dto.id,
        title = dto.title,
        posterUrl = "https://image.tmdb.org/t/p/w500${dto.posterPath}",
        voteAverage = dto.voteAverage,
        genres = details.genres.joinToString(", ") { it.name },
        duration = formatDuration(details.episodeRunTime.firstOrNull()),
        releaseDate = formatReleaseDate(dto.releaseDate)
    )

    private fun formatDuration(runtime: Int?): String {
        if (runtime == null) return ""
        val hours = runtime / 60
        val minutes = runtime % 60
        return "${hours}h ${minutes}m"
    }

    private fun formatReleaseDate(date: String?): String {
        return try {
            if (date.isNullOrBlank()) return ""
            val parsed = LocalDate.parse(date)
            "${parsed.year}, ${parsed.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${parsed.dayOfMonth}"
        } catch (e: Exception) {
            ""
        }
    }
}