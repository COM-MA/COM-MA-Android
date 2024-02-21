package com.green.comma.ui.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.repository.CardRepository
import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.BaseResponseNoData
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.card.ResponseCardRecogDetailDto
import kotlinx.coroutines.launch

class CardViewModel(private val cardRepository: CardRepository) : ViewModel() {

    private val _cardListItems = MutableLiveData<List<ResponseCardListDto>>()
    val cardListItems: LiveData<List<ResponseCardListDto>> = _cardListItems

    private val _cardDetailItem = MutableLiveData<ResponseCardDetailDto>()
    val cardDetailItem: LiveData<ResponseCardDetailDto> = _cardDetailItem

    private val _cardRecogDetailItem = MutableLiveData<ResponseCardRecogDetailDto>()
    val cardRecogDetailItem: LiveData<ResponseCardRecogDetailDto> = _cardRecogDetailItem

    private val _cardCreateResult = MutableLiveData<Boolean>()
    val cardCreateResult: LiveData<Boolean> = _cardCreateResult

    init {
        loadLatestCardList()
    }

    fun loadLatestCardList() {
        viewModelScope.launch {
            val latestCardList = cardRepository.getLatestCardList()
            _cardListItems.value = latestCardList
        }
    }

    fun loadAlphabetCardList() {
        viewModelScope.launch {
            val alphabetCardList = cardRepository.getAlphabetCardList()
            _cardListItems.value = alphabetCardList
        }
    }

    fun loadCardDetail(userCardId: Long) {
        viewModelScope.launch {
            val cardDetail = cardRepository.getCardDetail(userCardId)
            _cardDetailItem.value = cardDetail
        }
    }

    fun loadCardRecogDetail(name: String) {
        viewModelScope.launch {
            val cardRecogDetail = cardRepository.getCardRecogDetail(name)
            _cardRecogDetailItem.value = cardRecogDetail
        }
    }

    fun postCardCreate(cardId: Long){
        viewModelScope.launch {
            val cardRecogDetail = cardRepository.postCardCreate(cardId)
            _cardCreateResult.value = cardRecogDetail
        }
    }
}