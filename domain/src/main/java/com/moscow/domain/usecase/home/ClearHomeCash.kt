package com.moscow.domain.usecase.home

import com.moscow.domain.repository.HomeRepository
import javax.inject.Inject

class ClearHomeCash @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() =
        homeRepository.clearHomeCash()
}