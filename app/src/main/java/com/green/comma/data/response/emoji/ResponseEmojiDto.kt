package com.green.comma.data.response.emoji

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Year
@Serializable
data class ResponseEmojiDto(
    @SerialName("parentEmotion")
    val parentEmotion: String,
    @SerialName("childEmotion")
    val childEmotion: String,
)