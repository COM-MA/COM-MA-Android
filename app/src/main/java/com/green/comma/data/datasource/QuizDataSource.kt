package com.green.comma.data.datasource

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.response.quiz.ResponseQuizDataDto

interface QuizDataSource {
    suspend fun getQuizData(userCardId: Long) : BaseResponse<ResponseQuizDataDto>
}