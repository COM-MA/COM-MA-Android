package com.green.comma.data.request.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestCardCreateDto(
    @SerialName("cardImageUrl")
    val cardImageUrl: String,
    @SerialName("signLanguageDescription")
    val signLanguageDescription: String,
)