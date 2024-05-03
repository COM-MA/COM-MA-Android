package com.green.comma.data.datasource

import com.green.comma.data.request.emoji.RequestEmotionDto
import com.green.comma.data.service.EmojiService

class EmojiRemoteDataSource(private val emojiService : EmojiService): EmojiDataSource {
    override suspend fun postEmoji(data: RequestEmotionDto): Boolean {
        return runCatching { emojiService.postEmoji(data) }.isSuccess
    }
}