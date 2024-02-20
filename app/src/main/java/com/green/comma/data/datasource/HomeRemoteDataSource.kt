package com.green.comma.data.datasource

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.response.home.ResponseHomeDataDto
import com.green.comma.data.service.CardService
import com.green.comma.data.service.HomeService

class HomeRemoteDataSource(private val homeService : HomeService) : HomeDataSource {
    override suspend fun getHomeData(): BaseResponse<ResponseHomeDataDto> {
        return homeService.getHomeData()
    }
}