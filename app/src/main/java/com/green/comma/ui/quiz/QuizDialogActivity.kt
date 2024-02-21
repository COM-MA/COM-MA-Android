package com.green.comma.ui.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.window.OnBackInvokedDispatcher
import androidx.compose.ui.Alignment
import com.green.comma.R
import com.green.comma.databinding.ActivityQuizBinding
import com.green.comma.databinding.ActivityQuizDialogBinding

class QuizDialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityQuizDialogBinding.inflate(layoutInflater)
        title = ""

        val answer = intent.getStringExtra("answer")
        val userAnswer = intent.getStringExtra("userAnswer")
        val isCorrect = (answer == userAnswer)
        setDialogView(isCorrect, answer)

        QuizScore.addCount()

        Handler(Looper.getMainLooper()).postDelayed({
            if(QuizScore.getTotalQuiz() < QuizScore.getCount()){
                setFinishDialogView()
            } else {
                finish()
            }
        }, 1000)

        setContentView(binding.root)
    }

    private fun setDialogView(isCorrect: Boolean, answer: String?){

        var comment: String
        var charResource: Int

        if(isCorrect){
            comment = getString(R.string.quiz_dialog_text_compliment)
            charResource = R.drawable.img_character_surprise
            QuizScore.addScore()
        } else {
            comment = getString(R.string.quiz_dialog_text_sorry)
            charResource = R.drawable.img_character_sorry
        }

        binding.tvComment.text = comment
        binding.imgCharacter.setImageResource(charResource)

        binding.tvAnswer.text = "정답: " + answer.toString()
    }

    private fun setFinishDialogView(){
        binding.imgCharacter.visibility = View.GONE
        binding.tvComment.text = getString(R.string.quiz_dialog_text_finish)
        binding.tvAnswer.text = "정답 개수 " + QuizScore.getScore() + "/" + QuizScore.getTotalQuiz()
        binding.tvComment.textAlignment = View.TEXT_ALIGNMENT_CENTER
        binding.tvAnswer.textAlignment = View.TEXT_ALIGNMENT_CENTER
        binding.btnFinish.visibility = View.VISIBLE
        binding.btnFinish.setOnClickListener {
            var intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return (event?.action == MotionEvent.ACTION_OUTSIDE)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}

