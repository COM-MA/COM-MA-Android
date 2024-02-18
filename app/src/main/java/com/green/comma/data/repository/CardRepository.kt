package com.green.comma.data.repository

import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.datasource.CardRemoteDataSource

class CardRepository(
    private val cardRemoteDataSource: CardRemoteDataSource
) {
    //private val client = ApiClient.getApiClient().create(CardService::class.java)
    /*suspend fun getLatestCardList(): BaseResponse<List<ResponseLatestCardListDto>> {
        return client.getLatestCardList()
    }*/
    suspend fun getLatestCardList(): List<ResponseCardListDto> {
        return cardRemoteDataSource.getLatestCardList().data!!
    }
    suspend fun getAlphabetCardList(): List<ResponseCardListDto> {
        return cardRemoteDataSource.getAlphabetCardList().data!!
    }
}