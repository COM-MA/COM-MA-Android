package com.green.comma.data.datasource

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.BaseResponseNoData
import com.green.comma.data.response.auth.ResponseGoogleLoginDto

interface AuthDataSource {
    suspend fun postGoogleLogin(code: String) : BaseResponse<ResponseGoogleLoginDto>
    suspend fun postNickname(nickname: String) : BaseResponseNoData
}