package com.green.comma.data.repository

import com.green.comma.data.datasource.AIRemoteDataSource
import com.green.comma.data.response.ai.ResponsePredictionDto
import okhttp3.MultipartBody

class AIRepository(
    private val aiRemoteDataSource: AIRemoteDataSource
) {
    suspend fun postSignPrediction(code:  MultipartBody.Part): Result<ResponsePredictionDto> {
        return runCatching { aiRemoteDataSource.postSignPrediction(code) }
    }
}