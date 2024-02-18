package com.green.comma.ui.card

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.green.comma.data.ApiClient
import com.green.comma.data.datasource.CardRemoteDataSource
import com.green.comma.data.repository.CardRepository
import retrofit2.create

class CardViewModelFactory( private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CardViewModel::class.java) -> {
                val repository = CardRepository( CardRemoteDataSource(ApiClient.getApiClient().create()))
                CardViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}