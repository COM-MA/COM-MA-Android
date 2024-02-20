package com.green.comma.ui.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.green.comma.R
import com.green.comma.data.response.quiz.ResponseQuizDataDto
import com.green.comma.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private val quizViewModel: QuizViewModel by viewModels { QuizViewModelFactory(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        binding = ActivityQuizBinding.inflate(layoutInflater)

        val list = intent.getLongArrayExtra("selectedList")
        if (list != null && list.isNotEmpty()) {
            quizViewModel.loadQuizData(list[0])
        }

        quizViewModel.quizDataItem.observe(this, Observer { item ->
            setQuiz(item)
        })
        setContentView(binding.root)
    }

    private fun setQuiz(item: ResponseQuizDataDto){
        println(item)
        println(item.correctCard.cardImageUrl)
        println(item.correctCard.signImageUrl)
        Glide.with(this)
            .load(item.correctCard.signImageUrl)
            .into(binding.imgSign)

        Glide.with(this)
            .load(item.correctCard.cardImageUrl)
            .into(binding.imgCorrect)

        Glide.with(this)
            .load(item.wrongCard.cardImageUrl)
            .into(binding.imgWrong)

        binding.tvCorrect.text = item.correctCard.name
        binding.tvWrong.text = item.wrongCard.name
    }
}