package com.moscow.remote.dto.rating

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

@Serializable
data class UserRatingResponse(
    @SerialName("favorite")
    val favorite: Boolean?,
    @SerialName("id")
    val id: Int?,
    @SerialName("rated")
    @Serializable(with = UserRatingSerializer::class)
    val userRating: Int?,
    @SerialName("watchlist")
    val watchlist: Boolean?
)

object UserRatingSerializer : KSerializer<Int?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Rated", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Int? {
        val input = decoder as? JsonDecoder ?: error("Expected JsonDecoder")
        val element = input.decodeJsonElement()

        return when (element) {
            is JsonObject -> {
                // If rated = { "value": 3 }
                val rating = element["value"]?.jsonPrimitive?.floatOrNull
                rating?.toInt()
            }

            is JsonPrimitive -> {
                // If rated = false
                if (element.booleanOrNull == false) null
                else throw SerializationException("Unexpected value for rated: $element")
            }

            else -> null
        }
    }

    override fun serialize(encoder: Encoder, value: Int?) {
        val output = encoder as? JsonEncoder ?: error("Expected JsonEncoder")
        if (value == null) {
            output.encodeJsonElement(JsonPrimitive(false))
        } else {
            output.encodeJsonElement(buildJsonObject {
                put("value", value)
            })
        }
    }
}
