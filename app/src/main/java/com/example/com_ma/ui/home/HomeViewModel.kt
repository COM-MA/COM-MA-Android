package com.example.com_ma.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "언어천재님, 반가워요!"
    }
    val text: LiveData<String> = _text
}