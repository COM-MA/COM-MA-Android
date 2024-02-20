package com.green.comma.data.repository

import com.green.comma.data.datasource.HomeRemoteDataSource
import com.green.comma.data.datasource.QuizRemoteDataSource
import com.green.comma.data.response.home.ResponseHomeDataDto
import com.green.comma.data.response.quiz.ResponseQuizDataDto

class HomeRepository(
    private val homeRemoteDataSource: HomeRemoteDataSource
) {
    suspend fun getHomeData(): ResponseHomeDataDto {
        return homeRemoteDataSource.getHomeData().data!!
    }
}