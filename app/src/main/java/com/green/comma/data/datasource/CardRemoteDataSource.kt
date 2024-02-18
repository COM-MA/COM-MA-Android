package com.green.comma.data.datasource

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.card.ResponseLatestCardListDto
import com.green.comma.data.service.CardService

class CardRemoteDataSource(private val cardService : CardService) : CardDataSource {
    override suspend fun getLatestCardList(): BaseResponse<List<ResponseLatestCardListDto>> {
        return cardService.getLatestCardList()
    }
}