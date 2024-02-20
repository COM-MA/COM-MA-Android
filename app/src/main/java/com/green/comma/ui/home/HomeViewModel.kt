package com.green.comma.ui.home

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

    init {
        loadHomeData()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "언어천재님, 반가워요!"
    }
    val text: LiveData<String> = _text

    fun loadHomeData() {
        viewModelScope.launch {
            val homeData = homeRepository.getHomeData()
            _homeDataItem.value = homeData
        }
    }
}