package com.green.comma.data.datasource

import androidx.activity.viewModels
import com.green.comma.data.response.BaseResponse
import com.green.comma.data.response.quiz.ResponseQuizDataDto
import com.green.comma.data.service.QuizService
import com.green.comma.databinding.ActivityCardDetailBinding
import com.green.comma.databinding.ActivityQuizBinding
import com.green.comma.ui.card.CardViewModel
import com.green.comma.ui.card.CardViewModelFactory
import com.green.comma.ui.quiz.QuizViewModelFactory

class QuizRemoteDataSource(private val quizService : QuizService) : QuizDataSource {

    override suspend fun getQuizData(userCardId: Long): BaseResponse<ResponseQuizDataDto>  {
        return quizService.getQuizData(userCardId)
    }
}