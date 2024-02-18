package com.green.comma.data.datasource

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.card.ResponseLatestCardListDto

interface CardDataSource {
    suspend fun getLatestCardList() : BaseResponse<List<ResponseLatestCardListDto>>
}