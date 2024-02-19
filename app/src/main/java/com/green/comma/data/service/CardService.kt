package com.green.comma.data.service

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.card.ResponseCardListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardService {
    @GET("/api/card/lastest")
    suspend fun getLatestCardList(
    ): BaseResponse<List<ResponseCardListDto>>
    @GET("/api/card/alphabet")
    suspend fun getAlphabetCardList(
    ): BaseResponse<List<ResponseCardListDto>>
    @GET("/api/card/detail/{userCardId}")
    suspend fun getCardDetailList(
        @Path("userCardId") userCardId: Long,
    ): BaseResponse<ResponseCardDetailDto>
}