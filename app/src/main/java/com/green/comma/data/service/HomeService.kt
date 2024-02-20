package com.green.comma.data.service

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.home.ResponseHomeDataDto
import com.green.comma.data.response.quiz.ResponseQuizDataDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeService {
    @GET("/api/user/home")
    suspend fun getHomeData(
    ): BaseResponse<ResponseHomeDataDto>
}