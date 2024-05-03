package com.green.comma.data.response.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearchResultDto(
    @SerialName("word")
    val word: String,
    @SerialName("description")
    val description: String,
    @SerialName("partsOfSeech")
    val partsOfSeech: String
)