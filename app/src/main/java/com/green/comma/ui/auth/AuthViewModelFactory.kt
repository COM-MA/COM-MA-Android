package com.green.comma.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.green.comma.data.ApiClient
import com.green.comma.data.datasource.AuthRemoteDataSource
import com.green.comma.data.repository.AuthRepository
import retrofit2.create

class AuthViewModelFactory(private val context: Context, private val isGoogleLogin: Boolean): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                if(isGoogleLogin){
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