package com.green.comma.data.datasource

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.fairytale.ResponseFairytaleDetailDto
import com.green.comma.data.response.fairytale.ResponseFairytaleListDto

interface FairytaleDataSource {
    suspend fun getFairytaleList() : BaseResponse<List<ResponseFairytaleListDto>>
    suspend fun getFairytaleDetail(farirytaleId: Long) : BaseResponse<ResponseFairytaleDetailDto>
}