package com.green.comma.ui.fairytale

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.repository.CardRepository
import com.green.comma.data.repository.FairytaleRepository
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.response.fairytale.ResponseFairytaleListDto
import kotlinx.coroutines.launch

class FairytaleViewModel(private val fairytaleRepository: FairytaleRepository) : ViewModel() {

    private val _items = MutableLiveData<List<ResponseFairytaleListDto>>()
    val items: LiveData<List<ResponseFairytaleListDto>> = _items

    init {
        loadFairytaleList()
    }

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    fun loadFairytaleList() {
        println("동화 로드")
        viewModelScope.launch {
            val fairytaleList = fairytaleRepository.getFairytaleList()
            _items.value = fairytaleList
        }
    }
}