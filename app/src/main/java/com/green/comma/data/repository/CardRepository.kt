package com.green.comma.data.repository

import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.datasource.CardRemoteDataSource
import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.BaseResponseNoData
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.card.ResponseCardRecogDetailDto

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
    suspend fun getCardRecogDetail(name: String): ResponseCardRecogDetailDto {
        return cardRemoteDataSource.getCardRecogDetail(name).data!!
    }
    suspend fun postCardCreate(cardId: Long): Boolean {
        val result = cardRemoteDataSource.postCardCreate(cardId)
        println(result)
        return result
    }
}