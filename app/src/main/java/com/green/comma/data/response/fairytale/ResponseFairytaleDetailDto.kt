package com.green.comma.data.response.fairytale

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Year
@Serializable
data class ResponseFairytaleDetailDto(
    @SerialName("description")
    val description: String,
    @SerialName("recommendImageUrl")
    val recommendImageUrl: String,
)