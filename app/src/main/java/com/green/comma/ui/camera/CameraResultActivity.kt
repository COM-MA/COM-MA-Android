package com.green.comma.ui.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.green.comma.R
import com.green.comma.util.Tts
import com.green.comma.databinding.ActivityCameraResultBinding
import com.green.comma.ui.card.CardViewModel
import com.green.comma.ui.card.CardViewModelFactory

class CameraResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraResultBinding
    private val cardViewModel: CardViewModel by viewModels { CardViewModelFactory(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_result)

        binding = ActivityCameraResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wordResult = intent.getStringExtra("result")

        if(wordResult != null){
            cardViewModel.loadCardRecogDetail(wordResult)

            setResultView(wordResult)
        }

        val tts = Tts(applicationContext)
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

    private fun setResultView(wordResult: String){
        cardViewModel.cardRecogDetailItem.observe(this) {
            println(it)
            binding.textWord.text = wordResult
            Glide.with(this)
                .load(it.CardImageUrl)
                .into(binding.imgCard)
            Glide.with(this)
                .load(it.SignImageUrl)
                .into(binding.imgSign)
        }
    }
}