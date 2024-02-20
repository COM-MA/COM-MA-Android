package com.green.comma.data.service

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.quiz.ResponseQuizDataDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuizService {
    @POST("/api/card/quiz/{userCardId}")
    suspend fun getQuizData(
        @Path("userCardId") userCardId: Long,
    ): BaseResponse<ResponseQuizDataDto>
}