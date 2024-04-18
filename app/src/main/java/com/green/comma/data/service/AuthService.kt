package com.green.comma.data.service

import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.auth.ResponseGoogleLoginDto
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("/login/oauth2/code/google")
    suspend fun postGoogleLogin(
        @Query("code") code: String,
    ): BaseResponse<ResponseGoogleLoginDto>
}