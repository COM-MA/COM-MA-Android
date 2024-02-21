package com.green.comma.data.datasource

import android.app.Notification
import android.net.http.HttpException
import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.BaseResponseNoData
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.response.card.ResponseCardRecogDetailDto
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
    override suspend fun getCardRecogDetail(name: String): BaseResponse<ResponseCardRecogDetailDto> {
        return cardService.getCardRecogDetail(name)
    }
    override suspend fun postCardCreate(cardId: Long): Boolean {
        return runCatching { cardService.postCardCreate(cardId) }.isSuccess
    }
    override suspend fun deleteCard(userCardId: Long): Boolean {
        return runCatching { cardService.deleteCard(userCardId) }.isSuccess
    }
}