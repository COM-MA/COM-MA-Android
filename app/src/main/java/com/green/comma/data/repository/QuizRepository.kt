package com.green.comma.data.repository

import com.green.comma.data.datasource.QuizRemoteDataSource
import com.green.comma.data.response.quiz.ResponseQuizDataDto

class QuizRepository(
    private val quizRemoteDataSource: QuizRemoteDataSource
) {
    suspend fun getQuizData(userCardId: Long): ResponseQuizDataDto {
        return quizRemoteDataSource.getQuizData(userCardId).data!!
    }
}