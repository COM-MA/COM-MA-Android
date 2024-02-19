package com.green.comma.data.repository

import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.datasource.CardRemoteDataSource
import com.green.comma.data.response.card.ResponseCardDetailDto

class CardRepository(
    private val cardRemoteDataSource: CardRemoteDataSource
) {
    suspend fun getLatestCardList(): List<ResponseCardListDto> {
        return cardRemoteDataSource.getLatestCardList().data!!
    }
    suspend fun getAlphabetCardList(): List<ResponseCardListDto> {
        return cardRemoteDataSource.getAlphabetCardList().data!!
    }
    suspend fun getCardDetail(userCardId: Long): ResponseCardDetailDto {
        return cardRemoteDataSource.getCardDetail(userCardId).data!!
    }
}