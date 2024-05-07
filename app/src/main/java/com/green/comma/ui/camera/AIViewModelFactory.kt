package com.green.comma.ui.camera

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.green.comma.data.ApiClient
import com.green.comma.data.datasource.AIRemoteDataSource
import com.green.comma.data.repository.AIRepository
import retrofit2.create

// View Model 생성하는 클래스
class AIViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // modelClass가 AuthViewModel 클래스의 하위 클래스인지 확인
            modelClass.isAssignableFrom(AIViewModel::class.java) -> {
                val repository = AIRepository( AIRemoteDataSource(ApiClient.getApiClientForAI(context).create()))
                AIViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}