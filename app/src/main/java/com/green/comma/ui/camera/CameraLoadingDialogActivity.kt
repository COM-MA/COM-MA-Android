package com.green.comma.ui.camera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.green.comma.R
import com.green.comma.ui.quiz.QuizScore

class CameraLoadingDialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_loading_dialog)
        title = ""

        Handler(Looper.getMainLooper()).postDelayed({
            var intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        }, 1500)
    }
}