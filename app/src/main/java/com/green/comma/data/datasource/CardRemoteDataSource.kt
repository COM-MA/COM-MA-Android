package com.green.comma.data.datasource

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.service.CardService

class CardRemoteDataSource(private val cardService : CardService) : CardDataSource {
    override suspend fun getLatestCardList(): BaseResponse<List<ResponseCardListDto>> {
        return cardService.getLatestCardList()
    }
    override suspend fun getAlphabetCardList(): BaseResponse<List<ResponseCardListDto>> {
        return cardService.getAlphabetCardList()
    }

    override suspend fun getCardDetail(userCardId: Long): BaseResponse<ResponseCardDetailDto> {
        return cardService.getCardDetailList(userCardId)
    }
}