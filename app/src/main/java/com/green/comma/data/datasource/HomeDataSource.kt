package com.green.comma.data.datasource

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.response.home.ResponseHomeDataDto

interface HomeDataSource {
    suspend fun getHomeData() : BaseResponse<ResponseHomeDataDto>
}