package com.green.comma.ui.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.ApiClient
import com.green.comma.data.datasource.CardRemoteDataSource
import com.green.comma.data.response.card.ResponseLatestCardListDto
import com.green.comma.data.repository.CardRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.create

//class CardViewModel(private val cardRepository: CardRepository) : ViewModel() {
class CardViewModel(private val cardRepository: CardRepository) : ViewModel() {

    //private val cardRepository = CardRepository(CardRemoteDataSource(ApiClient.getApiClient().create()))
    private val _items = MutableLiveData<List<ResponseLatestCardListDto>>()
    val items: LiveData<List<ResponseLatestCardListDto>> = _items
    /*private val _items = MutableLiveData<String>().apply {
        value = "https://img.hankyung.com/photo/201803/03.16142212.1.jpg"
    }
    val items: LiveData<String> = _items*/

    init {
        loadLatestCardList()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "asdfasdfasdf"
    }
    val text: LiveData<String> = _text
    fun loadLatestCardList() {
        viewModelScope.launch {
            val latestCardList = cardRepository.getLatestCardList()
            _items.value = latestCardList
        }
    }
}