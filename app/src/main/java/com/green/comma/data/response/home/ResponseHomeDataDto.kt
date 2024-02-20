package com.green.comma.data.response.home

import com.green.comma.data.response.card.ResponseCardListDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseHomeDataDto(
    @SerialName("top2Fairytales")
    val top2Fairytales: List<topFairytaleDto>,
    @SerialName("top5Cards")
    val top5Cards: List<ResponseCardListDto>,
    @SerialName("home")
    val home: homeEventDto,
)

@Serializable
data class topFairytaleDto(
    @SerialName("id")
    val id: Long,
    @SerialName("recommendImageUrl")
    val recommendImageUrl: String,
)

@Serializable
data class topCardDto(
    @SerialName("userCardId")
    val userCardId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("cardImageUrl")
    val cardImageUrl: String,
    @SerialName("signImageUrl")
    val signImageUrl: String,
)

@Serializable
data class homeEventDto(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("isWordRegistered")
    val isWordRegistered: Boolean,
    @SerialName("isQuizParticipated")
    val isQuizParticipated: Boolean,
    @SerialName("isFairyTalePlayed")
    val isFairyTalePlayed: Boolean,
)
