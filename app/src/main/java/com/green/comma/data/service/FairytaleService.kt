package com.green.comma.data.service

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.fairytale.ResponseFairytaleDetailDto
import com.green.comma.data.response.fairytale.ResponseFairytaleListDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FairytaleService {
    @GET("/api/fairytale")
    suspend fun getFairytaleList(
    ): BaseResponse<List<ResponseFairytaleListDto>>
    @POST("/api/fairytale/detail/{farirytaleId}")
    suspend fun getFairytaleDetail(
        @Path("farirytaleId") farirytaleId: Long,
    ): BaseResponse<ResponseFairytaleDetailDto>
}