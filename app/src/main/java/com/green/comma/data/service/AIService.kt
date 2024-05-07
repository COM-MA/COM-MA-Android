package com.green.comma.data.service

import com.green.comma.data.response.ai.ResponsePredictionDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AIService {
    @Multipart
    @POST("/upload")
    suspend fun postSignPrediction(
        @Part file: MultipartBody.Part
    ): ResponsePredictionDto
}