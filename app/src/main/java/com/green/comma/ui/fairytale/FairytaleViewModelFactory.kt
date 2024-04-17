package com.green.comma.ui.fairytale

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.green.comma.data.ApiClient
import com.green.comma.data.datasource.CardRemoteDataSource
import com.green.comma.data.datasource.FairytaleDataSource
import com.green.comma.data.datasource.FairytaleRemoteDataSource
import com.green.comma.data.repository.CardRepository
import com.green.comma.data.repository.FairytaleRepository
import retrofit2.create

class FairytaleViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FairytaleViewModel::class.java) -> {
                val repository = FairytaleRepository(FairytaleRemoteDataSource(ApiClient.getApiClient(context).create()))
                FairytaleViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}