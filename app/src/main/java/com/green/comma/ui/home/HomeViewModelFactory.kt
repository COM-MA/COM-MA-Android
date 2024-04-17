package com.green.comma.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.green.comma.data.ApiClient
import com.green.comma.data.datasource.HomeDataSource
import com.green.comma.data.datasource.HomeRemoteDataSource
import com.green.comma.data.repository.HomeRepository
import retrofit2.create

class HomeViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                val repository = HomeRepository( HomeRemoteDataSource(ApiClient.getApiClient(context).create()))
                HomeViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}