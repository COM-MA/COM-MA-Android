package com.green.comma.data.datasource

import com.green.comma.data.request.emoji.RequestEmotionDto
import com.green.comma.data.response.BaseResponseNoData
import com.green.comma.data.response.emoji.ResponseEmojiDto

interface EmojiDataSource {
    suspend fun postEmoji(data: RequestEmotionDto) : Boolean
    suspend fun getEmoji() : ResponseEmojiDto?
}