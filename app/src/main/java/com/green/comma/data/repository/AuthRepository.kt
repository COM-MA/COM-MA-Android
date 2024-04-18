package com.green.comma.data.repository

import com.green.comma.data.datasource.AuthRemoteDataSource
import com.green.comma.data.response.auth.ResponseGoogleLoginDto

class AuthRepository(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    suspend fun postGoogleLogin(code: String): ResponseGoogleLoginDto {
        return authRemoteDataSource.postGoogleLogin(code).data!!
    }
}