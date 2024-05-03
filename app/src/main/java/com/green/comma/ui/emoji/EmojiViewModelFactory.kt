package com.green.comma.ui.emoji

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.green.comma.data.ApiClient
import com.green.comma.data.datasource.CardRemoteDataSource
import com.green.comma.data.datasource.EmojiRemoteDataSource
import com.green.comma.data.repository.CardRepository
import com.green.comma.data.repository.EmojiRepository
import retrofit2.create

class EmojiViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(EmojiViewModel::class.java) -> {
                val repository = EmojiRepository( EmojiRemoteDataSource(ApiClient.getApiClient(context).create()))
                EmojiViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}