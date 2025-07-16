package com.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorSocialMediaDto(
    @SerialName("facebook_id")
    val facebookId: String?,
    @SerialName("freebase_id")
    val freebaseId: String?,
    @SerialName("freebase_mid")
    val freebaseMid: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("instagram_id")
    val instagramId: String?,
    @SerialName("tiktok_id")
    val tiktokId: String?,
    @SerialName("tvrage_id")
    val tvrageId: Int?,
    @SerialName("twitter_id")
    val twitterId: String?,
    @SerialName("wikidata_id")
    val wikidataId: String?,
    @SerialName("youtube_id")
    val youtubeId: String?
)