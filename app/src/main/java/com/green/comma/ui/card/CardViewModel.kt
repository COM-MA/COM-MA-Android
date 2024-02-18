package com.green.comma.ui.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.repository.CardRepository
import kotlinx.coroutines.launch

class CardViewModel(private val cardRepository: CardRepository) : ViewModel() {

    private val _items = MutableLiveData<List<ResponseCardListDto>>()
    val items: LiveData<List<ResponseCardListDto>> = _items

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

    fun loadAlphabetCardList() {
        viewModelScope.launch {
            val alphabetCardList = cardRepository.getAlphabetCardList()
            _items.value = alphabetCardList
        }
    }

}