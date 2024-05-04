package com.green.comma.ui.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.green.comma.R
import com.green.comma.data.response.card.ResponseCardDetailDto
import com.green.comma.util.Tts
import com.green.comma.databinding.ActivityCardDetailBinding

class CardDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardDetailBinding
    private val cardViewModel: CardViewModel by viewModels { CardViewModelFactory(applicationContext) }
    private val isReversed: MutableLiveData<Boolean> = MutableLiveData(false)
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

        binding.imgCardSign.setOnClickListener {
            if(isReversed.value == true){
                if(binding.llSignDescr.visibility == View.VISIBLE)
                    binding.llSignDescr.visibility = View.INVISIBLE
                else binding.llSignDescr.visibility = View.VISIBLE
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
        binding.tvSignDescr.text = item.signLanguageDescription

        binding.btnCardReverse.setOnClickListener{
            isReversed.value = isReversed.value != true
        }

        isReversed.observe(this) {
            binding.tvCardName.visibility = if(it) View.INVISIBLE else View.VISIBLE
            binding.imgCardImage.visibility = if(it) View.INVISIBLE else View.VISIBLE
            binding.imgCardSign.visibility = if(it) View.VISIBLE else View.INVISIBLE
        }
    }
}