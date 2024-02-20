package com.green.comma.data.response.quiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseQuizDataDto(
    @SerialName("correctCard")
    val correctCard: QuizCardDto,
    @SerialName("wrongCard")
    val wrongCard: QuizCardDto,
)

@Serializable
data class QuizCardDto(
    @SerialName("name")
    val name: String,
    @SerialName("cardImageUrl")
    val cardImageUrl: String,
    @SerialName("signImageUrl")
    val signImageUrl: String
)
