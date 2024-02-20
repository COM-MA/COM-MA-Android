package com.green.comma.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.repository.CardRepository
import com.green.comma.data.repository.QuizRepository
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.data.response.quiz.ResponseQuizDataDto
import kotlinx.coroutines.launch

class QuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {

    private val _quizDataItem = MutableLiveData<ResponseQuizDataDto>()
    val quizDataItem: LiveData<ResponseQuizDataDto> = _quizDataItem

    fun loadQuizData(userCardId: Long) {
        viewModelScope.launch {
            val quizData = quizRepository.getQuizData(userCardId)
            _quizDataItem.value = quizData
        }
    }
}