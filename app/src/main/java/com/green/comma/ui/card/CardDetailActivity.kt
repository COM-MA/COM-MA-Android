package com.green.comma.ui.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.green.comma.R
import com.green.comma.TTS
import com.green.comma.databinding.ActivityCardDetailBinding
import com.green.comma.ui.WordCardView

class CardDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)
        var wordText = "호랑이"

        binding = ActivityCardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tts = TTS(applicationContext)
        tts.setTTS()

        binding.composeViewWordView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WordCardView({ tts.readTTS(wordText) }, wordText)
            }
        }
    }


}