package com.moscow.mapper

import com.moscow.domain.model.Series
import com.moscow.domain.usecase.series.GetRatedSeriesUseCase.RatedSeriesResult
import com.moscow.remote.dto.rating.series.RatedSeriesDto
import com.moscow.utils.IMAGES_URL
import kotlinx.datetime.toLocalDate

fun RatedSeriesDto.toOutputResult(): RatedSeriesResult? {
    val id = this.id ?: return null
    val name = this.name ?: return null
    val adult = (this.adult == true)
    val backdropPath = this.backdropPath ?: ""
    val releaseDate = try {
        this.firstAirDate?.toLocalDate() ?: return null
    } catch (e: Exception) {
        return null
    }
    val genreIds = this.genreIds?.filterNotNull() ?: emptyList()
    val originCountry = this.originCountry?.filterNotNull() ?: emptyList()
    val originalLanguage = this.originalLanguage ?: ""
    val originalName = this.originalName ?: ""
    val overview = this.overview ?: ""
    val posterPath = (IMAGES_URL + this.posterPath)
    val rating = this.rating?.toFloat() ?: return null

    return RatedSeriesResult(
        series = Series(
            id = id,
            name = name,
            rating = this.voteAverage?.toFloat() ?: 0f,
            adult = adult,
            backdropPath = backdropPath,
            firstAirDate = releaseDate,
            genreIds = genreIds,
            originCountry = originCountry,
            originalLanguage = originalLanguage,
            originalName = originalName,
            overview = overview,
            posterPath = posterPath
        ),
        rating = rating
    )
}