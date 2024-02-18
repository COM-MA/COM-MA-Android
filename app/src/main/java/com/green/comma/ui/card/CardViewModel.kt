package com.green.comma.ui.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.repository.CardRepository
import kotlinx.coroutines.launch

//class CardViewModel(private val cardRepository: CardRepository) : ViewModel() {
class CardViewModel(private val cardRepository: CardRepository) : ViewModel() {

    //private val cardRepository = CardRepository(CardRemoteDataSource(ApiClient.getApiClient().create()))
    private val _items = MutableLiveData<List<ResponseCardListDto>>()
    val items: LiveData<List<ResponseCardListDto>> = _items
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
        println("최신순 로드")
        viewModelScope.launch {
            val latestCardList = cardRepository.getLatestCardList()
            _items.value = latestCardList
        }
    }

    fun loadAlphabetCardList() {
        println("가나다순 로드")
        viewModelScope.launch {
            val alphabetCardList = cardRepository.getLatestCardList()
            _items.value = alphabetCardList
        }
    }

}