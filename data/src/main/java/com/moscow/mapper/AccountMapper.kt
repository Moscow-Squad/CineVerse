package com.moscow.mapper

import com.moscow.domain.model.profile.AccountDetails
import com.moscow.remote.dto.profile.AccountDto
import com.moscow.utils.IMAGES_URL


fun AccountDto.toDomain(): AccountDetails =
    AccountDetails(
        name = name.orEmpty(),
        username = userName.orEmpty(),
        image = avatar?.tmdb?.avatarPath?.let { IMAGES_URL + it }
    )
