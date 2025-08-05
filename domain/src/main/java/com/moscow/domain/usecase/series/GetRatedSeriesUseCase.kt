package com.moscow.domain.usecase.series

import com.moscow.domain.model.Series
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.PreferenceRepository
import com.moscow.domain.repository.SeriesRepository
import jakarta.inject.Inject

class GetRatedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val preferenceRepository: PreferenceRepository
) {

    suspend operator fun invoke(page: Int): List<RatedSeriesResult> {
        val user = preferenceRepository.getUser()
        val userid = if (user is UserType.AuthenticatedUser) user.id else "0"
        val parseUserid = userid.substring(
            startIndex = userid.indexOfFirst { it == '=' } + 1,
            endIndex = userid.indexOfFirst { it == ',' }
        )
        return seriesRepository.getRatedSeries(parseUserid.toInt(), page)
    }


    data class RatedSeriesResult(
        val series: Series,
        val rating: Float
    )
}