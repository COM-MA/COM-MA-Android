package com.green.comma.data.repository

import com.green.comma.data.response.card.ResponseLatestCardListDto
import com.green.comma.data.datasource.CardRemoteDataSource

class CardRepository(
    private val cardService: CardRemoteDataSource
) {
    //private val client = ApiClient.getApiClient().create(CardService::class.java)
    /*suspend fun getLatestCardList(): BaseResponse<List<ResponseLatestCardListDto>> {
        return client.getLatestCardList()
    }*/
    suspend fun getLatestCardList(): List<ResponseLatestCardListDto> {
        return cardService.getLatestCardList().data!!
    }
}