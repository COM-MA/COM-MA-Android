package com.green.comma.data.datasource

import com.green.comma.data.request.card.RequestCardCreateDto
import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.BaseResponseNoData
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.response.card.ResponseCardRecogDetailDto
import com.green.comma.data.response.card.ResponseSearchResultDto

interface CardDataSource {
    suspend fun getLatestCardList() : BaseResponse<List<ResponseCardListDto>>
    suspend fun getAlphabetCardList() : BaseResponse<List<ResponseCardListDto>>
    suspend fun getCardDetail(userCardId: Long) : BaseResponse<ResponseCardDetailDto>
    suspend fun getCardRecogDetail(searchWord: String) : BaseResponse<ResponseCardRecogDetailDto>
    suspend fun getSearchResultList(searchWord: String) : BaseResponse<List<ResponseSearchResultDto>>
    suspend fun postCardCreate(cardId: Long, requestData: RequestCardCreateDto) : BaseResponseNoData
    suspend fun deleteCard(userCardId: Long) : Boolean
}