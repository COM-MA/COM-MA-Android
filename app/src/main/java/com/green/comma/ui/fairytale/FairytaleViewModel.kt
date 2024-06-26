package com.green.comma.ui.fairytale

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.repository.FairytaleRepository
import com.green.comma.data.response.fairytale.ResponseFairytaleDetailDto
import com.green.comma.data.response.fairytale.ResponseFairytaleListDto
import kotlinx.coroutines.launch

class FairytaleViewModel(private val fairytaleRepository: FairytaleRepository) : ViewModel() {

    private val _fairytaleItems = MutableLiveData<List<ResponseFairytaleListDto>>()
    val fairytaleItems: LiveData<List<ResponseFairytaleListDto>> = _fairytaleItems

    private val _itemDetail = MutableLiveData<ResponseFairytaleDetailDto>()
    val itemDetail: LiveData<ResponseFairytaleDetailDto> = _itemDetail

    init {
        //loadFairytaleList()
    }

    fun loadFairytaleList() {
        viewModelScope.launch {
            val fairytaleList = fairytaleRepository.getFairytaleList()
            _fairytaleItems.value = fairytaleList
        }
    }
    fun loadFairytaleDetail(farirytaleId: Long) {
        viewModelScope.launch {
            val fairytaleDetail = fairytaleRepository.getFairytaleDetail(farirytaleId)
            _itemDetail.value = fairytaleDetail
        }
    }
}