package com.green.comma.data.datasource

import com.green.comma.data.response.ai.ResponsePredictionDto
import com.green.comma.data.service.AIService
import okhttp3.MultipartBody

class AIRemoteDataSource(private val aiService: AIService) : AIDataSource {
    override suspend fun postSignPrediction(file: MultipartBody.Part): ResponsePredictionDto {
        return aiService.postSignPrediction(file)
    }
}