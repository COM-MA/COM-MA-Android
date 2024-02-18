package com.green.comma.data.service

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.card.ResponseCardListDto
import retrofit2.http.GET

interface CardService {
    @GET("/api/card/lastest")
    suspend fun getLatestCardList(
    ): BaseResponse<List<ResponseCardListDto>>
    @GET("/api/card/alphabet")
    suspend fun getAlphabetCardList(
    ): BaseResponse<List<ResponseCardListDto>>
}