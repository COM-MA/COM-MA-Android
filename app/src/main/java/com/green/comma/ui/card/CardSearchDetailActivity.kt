package com.green.comma.ui.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.green.comma.R
import com.green.comma.data.request.card.RequestCardCreateDto
import com.green.comma.databinding.ActivityCardSearchDetailBinding
import com.green.comma.util.Tts

class CardSearchDetailActivity : AppCompatActivity() {
    private lateinit var searchWord: String
    private lateinit var binding: ActivityCardSearchDetailBinding
    private lateinit var tts: Tts
    private var cardId: Long = -1

    private val cardViewModel: CardViewModel by viewModels { CardViewModelFactory(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardSearchDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = Tts(applicationContext)
        tts.setTTS()

        searchWord = intent.getStringExtra(getString(R.string.search_word))!!
        if(searchWord.isNotEmpty()){
            cardViewModel.loadCardRecogDetail(searchWord, this)
        }

        setWordDetail()
    }

    private fun setWordDetail() {
        cardViewModel.cardRecogDetailItem.observe(this) {
            var wordText = it.word.trimStart()
            var cardUrl = it.cardImageUrl
            var signLanDescr = it.signLanguageDescription.trimStart()

            cardId = it.cardId

            binding.tvWord.text = wordText
            binding.tvCreateCardWord.text = wordText
            binding.tvWordForm.text = it.partsOfSeech.trimStart()
            binding.tvWordDescr.text = it.description.trimStart()
            binding.tvSignDescr.text = signLanDescr

            Glide.with(this)
                .load(cardUrl)
                .into(binding.imgCard)

            Glide.with(this)
                .load(cardUrl)
                .into(binding.imgCreateCardWord)

            Glide.with(this)
                .load(it.signImageUrl)
                .into(binding.imgSign)

            binding.btnCreateWordCard.setOnClickListener {
                setCardCreateBtn(cardUrl, signLanDescr)
            }

            binding.btnSpeaker.setOnClickListener {
                tts.readTTS(wordText, binding.btnSpeaker)
            }
        }
    }

    private fun setCardCreateBtn(cardUrl: String, signLanDescr: String) {
        if(!cardId.equals(-1)){
            val requestData = RequestCardCreateDto(cardUrl, signLanDescr)
            cardViewModel.postCardCreate(cardId, requestData, this)
        }
        cardViewModel.cardCreateResult.observe(this){
            if(it){
                val card = binding.cardViewCreateWord
                card.visibility = View.VISIBLE
                val animation = AnimationUtils.loadAnimation(this, R.anim.translate)
                card.startAnimation(animation)
                card.visibility = View.INVISIBLE
                binding.btnCreateWordCard.isClickable = false
                binding.tvCreateBtn.text = getString(R.string.camera_detail_btn_see_card)
                binding.imgCreateBtn.setImageResource(R.drawable.ic_bottom_nav_card_fill)
            } else {
                //Toast.makeText(this, "이미 등록된 단어예요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}