package com.green.comma.data.datasource

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.fairytale.ResponseFairytaleListDto
import com.green.comma.data.service.FairytaleService

class FairytaleRemoteDataSource(private val fairytaleService: FairytaleService): FairytaleDataSource {
    override suspend fun getFairytaleList(): BaseResponse<List<ResponseFairytaleListDto>> {
        return fairytaleService.getFairytaleList()
    }
}