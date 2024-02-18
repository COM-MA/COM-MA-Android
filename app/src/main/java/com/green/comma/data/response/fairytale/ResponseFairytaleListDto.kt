package com.green.comma.data.response.fairytale

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Year
@Serializable
data class ResponseFairytaleListDto(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("imgaeUrl")
    val imgaeUrl: String,
    @SerialName("channelName")
    val channelName: String,
    @SerialName("year")
    val year: String,
    @SerialName("time")
    val time: String,
    @SerialName("link")
    val link: String,
    @SerialName("subtitleTag")
    val subtitleTag: Boolean,
    @SerialName("signTag")
    val signTag: Boolean,
)