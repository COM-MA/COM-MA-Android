package com.green.comma.ui.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
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

        cardViewModel.cardDetailItem.observe(this, Observer { item ->
            setCard(item)
        })

        binding.btnCardSpeaker.setOnClickListener {
            tts.readTTS(binding.tvCardName.text.toString())
        }
        setContentView(binding.root)
    }

    private fun setCard(item: ResponseCardDetailDto){
        Glide.with(this)
            .load(item.cardImageUrl)
            .into(binding.imgVCardImage)
        binding.tvCardName.text = item.name
    }
}