package com.green.comma.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.green.comma.data.ApiClient
import com.green.comma.data.datasource.AuthRemoteDataSource
import com.green.comma.data.repository.AuthRepository
import retrofit2.create

// View Model 생성하는 클래스
class AuthViewModelFactory(private val context: Context, private val isGoogleLogin: Boolean): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // modelClass가 AuthViewModel 클래스의 하위 클래스인지 확인
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                if(isGoogleLogin){
                    // ApiClient.getApiClientForGoogleLogin(context).create() 는 Retrofit.create() -> Retrofit을 사용하여 서비스 구현체 생성
                    // -> AuthRemoteDataSource 클래스에서는 AuthService 인터페이스를 매개변수로 받음
                    val repository = AuthRepository( AuthRemoteDataSource(ApiClient.getApiClientForGoogleLogin(context).create()))
                    AuthViewModel(repository) as T
                } else {
                    val repository = AuthRepository( AuthRemoteDataSource(ApiClient.getApiClient(context).create()))
                    AuthViewModel(repository) as T
                }

            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}