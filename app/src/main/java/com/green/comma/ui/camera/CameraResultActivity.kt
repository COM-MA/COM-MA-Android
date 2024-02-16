package com.green.comma.ui.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.green.comma.R
import com.green.comma.TTS
import com.green.comma.databinding.ActivityCameraResultBinding
import com.green.comma.ui.WordCardView

class CameraResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_result)

        binding = ActivityCameraResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tts = TTS(applicationContext)
        tts.setTTS()

        var wordText = binding.textWord.text.toString()

        binding.btnSpeaker.setOnClickListener{
            tts.readTTS(wordText)
        }

        /*binding.composeViewWordCardView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WordCardView({ tts.readTTS(wordText) }, wordText)
            }
        }*/
    }
}