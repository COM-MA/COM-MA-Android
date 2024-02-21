package com.green.comma.ui.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
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
    private lateinit var tts: Tts
    private var cardId: Long = -1
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

        tts = Tts(applicationContext)
        tts.setTTS()

        binding.btnCreateWordCard.setOnClickListener {
            if(!cardId.equals(-1)){

                cardViewModel.postCardCreate(cardId)
            }
            cardViewModel.cardCreateResult.observe(this){
                println(it)
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
                    Toast.makeText(this, "이미 등록된 단어예요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setResultView(wordResult: String) {
        cardViewModel.cardRecogDetailItem.observe(this) {
            cardId = it.CardId
            binding.textWord.text = wordResult
            binding.tvCreateCardWord.text = wordResult
            Glide.with(this)
                .load(it.CardImageUrl)
                .into(binding.imgCard)
            Glide.with(this)
                .load(it.CardImageUrl)
                .into(binding.imgCreateCardWord)
            Glide.with(this)
                .load(it.SignImageUrl)
                .into(binding.imgSign)

            var wordText = binding.textWord.text.toString()

            binding.btnSpeaker.setOnClickListener {
                tts.readTTS(wordText, binding.btnSpeaker)
            }
        }
    }
}