package com.green.comma.ui.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.Observer
import com.green.comma.R
import com.green.comma.databinding.ActivityCardDetailBinding
import com.green.comma.databinding.ActivitySelectCardBinding
import com.green.comma.ui.compose.WordCardListItem

class SelectCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectCardBinding
    private val cardViewModel: CardViewModel by viewModels { CardViewModelFactory(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_card)
        binding = ActivitySelectCardBinding.inflate(layoutInflater)

        CardSelect.resetList()

        var columCount = 2

        cardViewModel.cardListItems.observe(this) {
            binding.composeViewWordCard.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    LazyVerticalGrid(
                        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding)),
                        columns = GridCells.Fixed(columCount)
                    ) {
                        items(it.size) { item ->
                            WordCardListItem(it[item], { }, true)
                        }
                    }
                    DisposableEffect(Unit) {
                        cardViewModel.loadLatestCardList()
                        onDispose {}
                    }
                }
            }
        }


        setContentView(binding.root)
    }
}