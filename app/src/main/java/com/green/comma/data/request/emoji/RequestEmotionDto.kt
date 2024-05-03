package com.green.comma.data.request.emoji

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestEmotionDto(
    @SerialName("parentEmotion")
    val parentEmotion: String,
    @SerialName("childEmotion")
    val childEmotion: String,
)