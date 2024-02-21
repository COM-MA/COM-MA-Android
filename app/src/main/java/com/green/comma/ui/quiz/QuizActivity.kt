package com.green.comma.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.green.comma.R
import com.green.comma.data.response.quiz.ResponseQuizDataDto
import com.green.comma.databinding.ActivityQuizBinding
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private val quizViewModel: QuizViewModel by viewModels { QuizViewModelFactory(applicationContext) }
    private val isCardLeftClicked = MutableLiveData<Boolean>()
    private val isCardRightClicked = MutableLiveData<Boolean>()
    private lateinit var answer: String
    private lateinit var btnNext: Button
    private lateinit var cardList: LongArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        btnNext = binding.btnNext

        cardList = intent.getLongArrayExtra("selectedList")!!
        if (cardList != null && cardList.isNotEmpty()) {
            quizViewModel.loadQuizData(cardList[0])
        }

        QuizScore.reset()
        QuizScore.setTotalQuiz(cardList.size)

        setCardView()
        setNextBtn()

        quizViewModel.quizDataItem.observe(this, Observer { item ->
            setQuizView(item)
        })
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        isCardLeftClicked.value = false
        isCardRightClicked.value = false
        btnNext.visibility = View.INVISIBLE

        binding.tvCount.text = QuizScore.getCount().toString() + " / " + cardList?.size
        val count = QuizScore.getCount()
        if(count > 1 && count < cardList.size + 1)
            quizViewModel.loadQuizData(cardList[(count-1)])
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            finish()
        }
    }

    private fun setQuizView(item: ResponseQuizDataDto){
        val random = Random
        val randomBool = random.nextBoolean()
        println(randomBool)
        answer = item.correctCard.name
        Glide.with(this)
            .load(item.correctCard.signImageUrl)
            .into(binding.imgSign)

        Glide.with(this)
            .load(item.correctCard.cardImageUrl)
            .into(if(randomBool) binding.imgLeft else binding.imgRight)

        Glide.with(this)
            .load(item.wrongCard.cardImageUrl)
            .into(if(randomBool) binding.imgRight else binding.imgLeft)

        binding.tvLeft.text = if(randomBool) item.wrongCard.name else item.correctCard.name
        binding.tvRight.text = if(randomBool) item.correctCard.name else item.wrongCard.name
    }

    private fun setCardView(){

        val cardViewLeft = binding.cardViewSelectLeft
        cardViewLeft.setOnClickListener{
            isCardLeftClicked.value = true
            isCardRightClicked.value = false
            btnNext.visibility = View.VISIBLE
        }

        val cardViewRight = binding.cardViewSelectRight
        cardViewRight.setOnClickListener{
            isCardLeftClicked.value = false
            isCardRightClicked.value = true
            btnNext.visibility = View.VISIBLE
        }

        isCardLeftClicked.observe(this) {
            cardViewLeft.strokeWidth = if(it) 6 else 0
        }

        isCardRightClicked.observe(this) {
            cardViewRight.strokeWidth = if(it) 6 else 0
        }
    }

    private fun setNextBtn() {
        btnNext.visibility = View.INVISIBLE
        binding.btnNext.setOnClickListener {
            var userAnswer: String = binding.tvLeft.text.toString()
            if(isCardRightClicked.value == true) userAnswer = binding.tvRight.text.toString()

            var intent = Intent(this, QuizDialogActivity::class.java)
            intent.putExtra("userAnswer", userAnswer)
            intent.putExtra("answer", answer)
            startActivityForResult(intent, 0)
        }
    }
}