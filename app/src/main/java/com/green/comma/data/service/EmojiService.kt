package com.green.comma.data.service

import com.green.comma.data.request.emoji.RequestEmotionDto
import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.BaseResponseNoData
import com.green.comma.data.response.emoji.ResponseEmojiDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
interface EmojiService {
    @POST("/api/user/emotion")
    suspend fun postEmoji(
        @Body body: RequestEmotionDto
    ): BaseResponseNoData

    @GET("/api/user/emotion")
    suspend fun getEmoji(
    ): BaseResponse<ResponseEmojiDto>
}