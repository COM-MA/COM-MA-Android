package com.green.comma.ui.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.green.comma.R
import com.green.comma.databinding.ActivityCardDetailBinding
import com.green.comma.ui.WordCardView

class CardDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        binding = ActivityCardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeViewWordView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WordCardView()
            }
        }
    }
}