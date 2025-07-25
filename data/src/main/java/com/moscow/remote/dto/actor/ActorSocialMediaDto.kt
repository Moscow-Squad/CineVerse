package com.moscow.remote.dto.actor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorSocialMediaDto(
    @SerialName("id")
    val id: Int,
    @SerialName("wikidata_id")
    val wikidataId: String?,
    @SerialName("facebook_id")
    val facebookId: String?,
    @SerialName("tiktok_id")
    val tiktokId: String?,
    @SerialName("twitter_id")
    val twitterId: String?,
    @SerialName("youtube_id")
    val youtubeId: String?,
    @SerialName("instagram_id")
    val instagramId: String?,
    @SerialName("freebase_id")
    val freebaseId: String?,
    @SerialName("freebase_mid")
    val freebaseMid: String?,
    @SerialName("imdb_id")
    val imdbId: String?
)
