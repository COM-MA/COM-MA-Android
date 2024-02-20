package com.green.comma.data.response.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCardRecogDetailDto(
    @SerialName("CardId")
    val CardId: Long,
    @SerialName("CardImageUrl")
    val CardImageUrl: String,
    @SerialName("SignImageUrl")
    val SignImageUrl: String,
)
