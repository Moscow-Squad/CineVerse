package com.moscow.mapper

import com.moscow.domain.model.UserInfo
import com.moscow.remote.dto.profile.AccountDto
import com.moscow.utils.IMAGES_URL


fun AccountDto.toDomain(): UserInfo =
    UserInfo(
        name = name.orEmpty(),
        username = userName.orEmpty(),
        image = avatar?.tmdb?.avatarPath?.let { IMAGES_URL + it }
    )
