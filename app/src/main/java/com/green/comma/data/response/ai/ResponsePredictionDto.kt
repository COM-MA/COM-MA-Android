package com.green.comma.data.response.ai

import kotlinx.serialization.Serializable

@Serializable
data class ResponsePredictionDto (
    val prediction: String
)
