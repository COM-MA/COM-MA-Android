package com.green.comma.ui.card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.dimensionResource
import com.green.comma.R
import com.green.comma.databinding.ActivityCardDetailBinding
import com.green.comma.databinding.ActivityCardSearchBinding
import com.green.comma.databinding.FragmentCardBinding
import com.green.comma.ui.compose.SearchResultListItem
import com.green.comma.ui.compose.WordCardListItem

class CardSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardSearchBinding.inflate(layoutInflater)

        setResultList()

        setContentView(binding.root)
    }

    private fun setResultList() {
        val result = "사과"
        binding.tvSearchResultDescr.visibility = View.VISIBLE
        binding.tvSearchResultDescr.text = "\"$result\"" + getString(R.string.card_search_tv_result)
        binding.composeViewWordSearchResult.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LazyColumn {
                    items(5) { idx ->
                        SearchResultListItem(
                            searchText = "사과",
                            title = "사과나무",
                            descr = "사과과고가나",
                            form = "형용사",
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}