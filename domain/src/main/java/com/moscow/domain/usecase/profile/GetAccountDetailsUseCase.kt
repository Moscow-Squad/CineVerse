package com.moscow.domain.usecase.profile

import com.moscow.domain.repository.ProfileRepository
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(accountId:String, sessionId:String) =
        profileRepository.getUserInfo(accountId,sessionId)
}