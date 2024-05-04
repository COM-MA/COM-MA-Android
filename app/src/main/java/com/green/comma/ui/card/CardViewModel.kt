package com.green.comma.ui.card

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.repository.CardRepository
import com.green.comma.data.request.card.RequestCardCreateDto
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.card.ResponseCardRecogDetailDto
import com.green.comma.data.response.card.ResponseSearchResultDto
import com.green.comma.ui.common.LoadingDialog
import kotlinx.coroutines.launch

class CardViewModel(private val cardRepository: CardRepository) : ViewModel() {

    private val _cardListItems = MutableLiveData<List<ResponseCardListDto>>()
    val cardListItems: LiveData<List<ResponseCardListDto>> = _cardListItems

    private val _cardDetailItem = MutableLiveData<ResponseCardDetailDto>()
    val cardDetailItem: LiveData<ResponseCardDetailDto> = _cardDetailItem

    private val _cardRecogDetailItem = MutableLiveData<ResponseCardRecogDetailDto>()
    val cardRecogDetailItem: LiveData<ResponseCardRecogDetailDto> = _cardRecogDetailItem

    private val _searchResultList = MutableLiveData<List<ResponseSearchResultDto>>()
    val searchResultList: LiveData<List<ResponseSearchResultDto>> = _searchResultList

    private val _cardCreateResult = MutableLiveData<Boolean>()
    val cardCreateResult: LiveData<Boolean> = _cardCreateResult

    private val _cardDeleteResult = MutableLiveData<Boolean>()
    val cardDeleteResult: LiveData<Boolean> = _cardDeleteResult

    init {
        //loadLatestCardList()
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

    fun loadCardRecogDetail(searchWord: String, context: Context): Boolean {
        val loadingDialog = LoadingDialog(context)
        var isSuccess = false
        viewModelScope.launch {
            loadingDialog.show()
            cardRepository.getCardRecogDetail(searchWord)
                .onSuccess {
                    _cardRecogDetailItem.value = it
                    loadingDialog.dismiss()
                    isSuccess = true
                }
                .onFailure {
                    Log.d("GET WORD DETAIL DATA FAILURE", it.toString())
                    Toast.makeText(context, "데이터를 가져오는데 실패했어요.", Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                    isSuccess = false
                }
        }

        return isSuccess
    }

    fun loadSearchResultList(name: String, context: Context) {
        val loadingDialog = LoadingDialog(context)
        viewModelScope.launch {
            loadingDialog.show()
            cardRepository.getSearchResultList(name)
                .onSuccess {
                    _searchResultList.value = it
                    loadingDialog.dismiss()
                }
                .onFailure {
                    Log.d("GET WORD SEARCH DATA FAILURE", it.toString())
                    Toast.makeText(context, "검색에 실패했어요.", Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
        }
    }

    fun postCardCreate(cardId: Long, requestData: RequestCardCreateDto, context: Context){
        viewModelScope.launch {
            cardRepository.postCardCreate(cardId, requestData)
                .onSuccess {
                    _cardCreateResult.value = true
                }
                .onFailure {
                    Log.d("POST CARD CREATE FAILURE", it.toString())
                    Toast.makeText(context, "이미 등록된 단어카드입니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun deleteCard(userCardId: Long){
        viewModelScope.launch {
            val cardDeleteResult = cardRepository.deleteCard(userCardId)
            _cardDeleteResult.value = cardDeleteResult
        }
    }


}