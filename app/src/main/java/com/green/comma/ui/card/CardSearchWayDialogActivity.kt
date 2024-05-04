package com.green.comma.ui.card

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.green.comma.R
import com.green.comma.databinding.ActivityCardSearchWayDialogBinding
import com.green.comma.databinding.DialogLoadingBinding
import com.green.comma.ui.camera.CameraActivity
import com.green.comma.ui.quiz.QuizScore

class CardSearchWayDialogActivity : AppCompatActivity() {

    lateinit var binding: ActivityCardSearchWayDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardSearchWayDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = ""

        binding.btnRecogWithWord.setOnClickListener {
            startActivity(Intent(this, CardSearchActivity::class.java))
            finish()
        }
        binding.btnRecogWithSign.setOnClickListener{
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}