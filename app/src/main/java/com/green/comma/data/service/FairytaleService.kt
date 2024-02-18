package com.green.comma.data.service

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.fairytale.ResponseFairytaleListDto
import retrofit2.http.GET

interface FairytaleService {
    @GET("/api/fairytale")
    suspend fun getFairytaleList(
    ): BaseResponse<List<ResponseFairytaleListDto>>
}