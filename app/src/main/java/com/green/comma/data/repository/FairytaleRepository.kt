package com.green.comma.data.repository

import com.green.comma.data.datasource.FairytaleRemoteDataSource
import com.green.comma.data.response.fairytale.ResponseFairytaleListDto

class FairytaleRepository(
private val fairytaleService: FairytaleRemoteDataSource
) {
    suspend fun getFairytaleList(): List<ResponseFairytaleListDto> {
        return fairytaleService.getFairytaleList().data!!
    }
}