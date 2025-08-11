package com.moscow.domain.model

data class CreditsInfo(
    val cast: List<CastInfo>,
    val crew: List<CrewInfo>
) {
    data class CastInfo(
        val id: Int,
        val originalName: String,
        val characterName: String,
        val profileImg: String,
    )

    data class CrewInfo(
        val id: Int,
        val name: String,
        val job: String,
    )
}