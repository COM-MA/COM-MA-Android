package com.green.comma.ui.quiz

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.green.comma.data.ApiClient
import com.green.comma.data.datasource.CardRemoteDataSource
import com.green.comma.data.datasource.QuizRemoteDataSource
import com.green.comma.data.repository.CardRepository
import com.green.comma.data.repository.QuizRepository
import retrofit2.create

class QuizViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(QuizViewModel::class.java) -> {
                val repository = QuizRepository( QuizRemoteDataSource(ApiClient.getApiClient().create()))
                QuizViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}