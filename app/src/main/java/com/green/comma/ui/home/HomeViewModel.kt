package com.green.comma.ui.home

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.repository.CardRepository
import com.green.comma.data.repository.HomeRepository
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.home.ResponseHomeDataDto
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _homeDataItem = MutableLiveData<ResponseHomeDataDto>()
    val homeDataItem: LiveData<ResponseHomeDataDto> = _homeDataItem

    /*init {
        loadHomeData()
    }*/

    fun loadHomeData() {
        viewModelScope.launch {
            homeRepository.getHomeData()
                .onSuccess {
                    _homeDataItem.value = it
                }
                .onFailure {
                    Log.d("GET HOME DATA FAILURE", it.toString())
                }
        }
    }
}