package com.green.comma.data.datasource

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.auth.ResponseGoogleLoginDto
import com.green.comma.data.service.AuthService

class AuthRemoteDataSource(private val authService : AuthService) : AuthDataSource {
    override suspend fun postGoogleLogin(code: String): BaseResponse<ResponseGoogleLoginDto> {
        return authService.postGoogleLogin(code)
    }
}