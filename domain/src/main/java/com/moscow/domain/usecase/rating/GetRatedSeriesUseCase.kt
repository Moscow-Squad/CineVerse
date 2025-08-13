package com.moscow.domain.usecase.rating

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

        val parseUserid = try {
            val equalIndex = userid.indexOfFirst { it == '=' }
            val commaIndex = userid.indexOfFirst { it == ',' }

            when {
                equalIndex == -1 || commaIndex == -1 || commaIndex <= equalIndex -> {
                    userid.toIntOrNull() ?: 0
                }

                else -> {
                    userid.substring(equalIndex + 1, commaIndex).toIntOrNull() ?: 0
                }
            }
        } catch (e: Exception) {
            0
        }

        return seriesRepository.getRatedSeries(parseUserid, page)
    }

    data class RatedSeriesResult(
        val series: Series,
        val rating: Float
    )
}