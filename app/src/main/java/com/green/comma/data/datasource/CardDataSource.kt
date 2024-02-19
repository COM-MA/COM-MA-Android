package com.green.comma.data.datasource

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.card.ResponseCardListDto

interface CardDataSource {
    suspend fun getLatestCardList() : BaseResponse<List<ResponseCardListDto>>
    suspend fun getAlphabetCardList() : BaseResponse<List<ResponseCardListDto>>
    suspend fun getCardDetail(userCardId: Long) : BaseResponse<ResponseCardDetailDto>
}