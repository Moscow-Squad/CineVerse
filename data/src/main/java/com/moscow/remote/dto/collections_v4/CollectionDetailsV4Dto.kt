package com.moscow.remote.dto.collections_v4


import com.moscow.domain.model.MediaItem
import com.moscow.domain.model.MediaType
import com.moscow.utils.IMAGES_URL
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionDetailsV4Dto(
    @SerialName("id")
    val id: Int,
    @SerialName("average_rating")
    val averageRating: Double? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("comments")
    val comments: Comments,
    @SerialName("created_by")
    val createdBy: CreatedBy? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("iso_3166_1")
    val iso31661: String? = null,
    @SerialName("iso_639_1")
    val iso6391: String? = null,
    @SerialName("item_count")
    val itemCount: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("object_ids")
    val objectIds: ObjectIds? = null,
    @SerialName("page")
    val page: Int? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("public")
    val `public`: Boolean? = null,
    @SerialName("results")
    val results: List<Result>,
    @SerialName("revenue")
    val revenue: Long? = null,
    @SerialName("runtime")
    val runtime: Int? = null,
    @SerialName("sort_by")
    val sortBy: String? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Int? = null
)

@Serializable
class ObjectIds

@Serializable
data class CreatedBy(
    @SerialName("id")
    val id: String,
    @SerialName("avatar_path")
    val avatarPath: String? = null,
    @SerialName("gravatar_hash")
    val gravatarHash: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("username")
    val username: String? = null
)


@Serializable
data class Result(
    @SerialName("id")
    val id: Int,
    @SerialName("adult")
    val adult: Boolean? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("video")
    val video: Boolean? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null
)

@Serializable
data class Comments(
    @SerialName("movie:284054")
    val movie284054: String? = null,
    @SerialName("movie:299534")
    val movie299534: String? = null,
    @SerialName("movie:299536")
    val movie299536: String? = null,
    @SerialName("movie:299537")
    val movie299537: String? = null,
    @SerialName("movie:363088")
    val movie363088: String? = null,
    @SerialName("movie:429617")
    val movie429617: String? = null,
    @SerialName("movie:447365")
    val movie447365: String? = null,
    @SerialName("movie:453395")
    val movie453395: String? = null,
    @SerialName("movie:497698")
    val movie497698: String? = null,
    @SerialName("movie:505642")
    val movie505642: String? = null,
    @SerialName("movie:524434")
    val movie524434: String? = null,
    @SerialName("movie:533535")
    val movie533535: String? = null,
    @SerialName("movie:566525")
    val movie566525: String? = null,
    @SerialName("movie:609681")
    val movie609681: String? = null,
    @SerialName("movie:616037")
    val movie616037: String? = null,
    @SerialName("movie:617127")
    val movie617127: String? = null,
    @SerialName("movie:634649")
    val movie634649: String? = null,
    @SerialName("movie:640146")
    val movie640146: String? = null,
    @SerialName("movie:822119")
    val movie822119: String? = null,
    @SerialName("movie:986056")
    val movie986056: String? = null
)

fun CollectionDetailsV4Dto.toDomain() = this.results.map { item ->
    MediaItem(
        adult = item.adult ?: false,
        backdropPath = IMAGES_URL + item.backdropPath,
        firstAirDate = item.releaseDate ?: "",
        genreIds = item.genreIds,
        id = item.id,
        mediaType = if (item.mediaType == "movie") MediaType.Movie else MediaType.Tv,
        name = item.title ?: "",
        originalName = item.originalTitle ?: "",
        overview = item.overview ?: "",
        popularity = item.popularity ?: 0.0,
        posterPath = IMAGES_URL + item.posterPath,
        rate = item.voteAverage?.toFloat() ?: 0f
    )
}