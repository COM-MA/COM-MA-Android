package com.green.comma.data.response.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCardRecogDetailDto(
    @SerialName("cardId")
    val cardId: Long,
    @SerialName("word")
    val word: String,
    @SerialName("description")
    val description: String,
    @SerialName("partsOfSeech")
    val partsOfSeech: String,
    @SerialName("cardImageUrl")
    val cardImageUrl: String,
    @SerialName("signImageUrl")
    val signImageUrl: String,
    @SerialName("signLanguageDescription")
    val signLanguageDescription: String,
)
