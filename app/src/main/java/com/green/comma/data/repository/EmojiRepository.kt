package com.green.comma.data.repository

import com.green.comma.data.datasource.AuthRemoteDataSource
import com.green.comma.data.datasource.EmojiRemoteDataSource
import com.green.comma.data.request.emoji.RequestEmotionDto
import com.green.comma.data.response.auth.ResponseGoogleLoginDto
import com.green.comma.data.response.emoji.ResponseEmojiDto

class EmojiRepository(
    private val emojiRemoteDataSource: EmojiRemoteDataSource
) {
    suspend fun postEmoji(data: RequestEmotionDto): Boolean {
        return emojiRemoteDataSource.postEmoji(data)
    }
    suspend fun getEmoji(): Result<ResponseEmojiDto?> {
        return runCatching { emojiRemoteDataSource.getEmoji() }
    }
}