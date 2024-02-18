package com.green.comma.data.service

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.card.ResponseLatestCardListDto
import retrofit2.http.GET
import retrofit2.http.Header

interface CardService {
    @GET("/api/card/lastest")
    suspend fun getLatestCardList(
    ): BaseResponse<List<ResponseLatestCardListDto>>
}