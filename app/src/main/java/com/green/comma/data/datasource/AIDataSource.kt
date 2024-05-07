package com.green.comma.data.datasource

import com.green.comma.data.response.ai.ResponsePredictionDto
import okhttp3.MultipartBody

interface AIDataSource {
    suspend fun postSignPrediction(file:  MultipartBody.Part) : ResponsePredictionDto
}