package com.green.comma.ui.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
            tts.readTTS(binding.tvCardName.text.toString(), binding.btnCardSpeaker)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnBigBack.setOnClickListener {
            finish()
        }

        binding.btnDelete.setOnClickListener {
            cardViewModel.deleteCard(userCardId)
            cardViewModel.cardDeleteResult.observe(this){
                if(it){
                    Toast.makeText(this, "삭제를 완료했어요", Toast.LENGTH_SHORT).show()
                    cardViewModel.loadLatestCardList()
                    finish()
                } else {
                    Toast.makeText(this, "삭제에 실패했어요", Toast.LENGTH_SHORT).show()
                }
            }
        }
        setContentView(binding.root)
    }

    private fun setCard(item: ResponseCardDetailDto){
        Glide.with(this)
            .load(item.cardImageUrl)
            .into(binding.imgCardImage)

        Glide.with(this)
            .load(item.signImageUrl)
            .into(binding.imgCardSign)

        binding.tvCardName.text = item.name

        binding.btnCardReverse.setOnClickListener{
            if(binding.imgCardSign.visibility == View.INVISIBLE) {
                binding.tvCardName.visibility = View.INVISIBLE
                binding.imgCardImage.visibility = View.INVISIBLE
                binding.imgCardSign.visibility = View.VISIBLE
            } else {
                binding.tvCardName.visibility = View.VISIBLE
                binding.imgCardImage.visibility = View.VISIBLE
                binding.imgCardSign.visibility = View.INVISIBLE
            }
        }
    }
}