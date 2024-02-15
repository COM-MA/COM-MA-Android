package com.green.comma.ui.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.green.comma.R
import com.green.comma.TTS
import com.green.comma.databinding.ActivityCameraResultBinding

class CameraResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_result)

        binding = ActivityCameraResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tts = TTS(applicationContext)
        tts.setTTS()

        var text = binding.textWord.text.toString()

        binding.btnSpeaker.setOnClickListener{
            tts.readTTS(text)
        }
    }
}