package com.green.comma.ui.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.green.comma.R
import com.green.comma.util.Tts
import com.green.comma.databinding.ActivityCardDetailBinding

class CardDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)
        var wordText = "호랑이"

        binding = ActivityCardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tts = Tts(applicationContext)
        tts.setTTS()

        /*binding.composeViewWordView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WordCardView({ tts.readTTS(wordText) }, wordText)
            }
        }*/
    }


}