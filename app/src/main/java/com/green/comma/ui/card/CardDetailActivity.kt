package com.green.comma.ui.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.green.comma.R
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.util.Tts
import com.green.comma.databinding.ActivityCardDetailBinding

class CardDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardDetailBinding
    private val cardViewModel: CardViewModel by viewModels { CardViewModelFactory(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)
        binding = ActivityCardDetailBinding.inflate(layoutInflater)

        val tts = Tts(applicationContext)
        val userCardId = intent.getLongExtra("userCardId", -1)

        if(!userCardId.equals(-1)){
            cardViewModel.loadCardDetail(userCardId)
        }

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