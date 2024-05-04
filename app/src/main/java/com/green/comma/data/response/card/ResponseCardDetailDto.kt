package com.green.comma.data.response.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCardDetailDto(
    @SerialName("name")
    val name: String,
    @SerialName("cardImageUrl")
    val cardImageUrl: String,
    @SerialName("signImageUrl")
    val signImageUrl: String,
    @SerialName("signLanguageDescription")
    val signLanguageDescription: String
)
