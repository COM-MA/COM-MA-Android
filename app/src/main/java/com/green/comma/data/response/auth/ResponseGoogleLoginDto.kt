package com.green.comma.data.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGoogleLoginDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("nickname")
    val nickname: String,
)