package com.green.comma.ui.card

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.green.comma.R
import com.green.comma.databinding.ActivityCardSearchBinding
import com.green.comma.ui.compose.SearchResultListItem


class CardSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardSearchBinding
    private val cardViewModel: CardViewModel by viewModels { CardViewModelFactory(applicationContext) }
    private var inputWord: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardSearchBinding.inflate(layoutInflater)

        setKeyboard()
        binding.btnSearch.setOnClickListener {
            setResultList()
        }

        setContentView(binding.root)
    }

    private fun setKeyboard() {
        binding.editSearchWord.setOnKeyListener(View.OnKeyListener { _, keyCode, event -> //Enter key Action
            if (event.action === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // 키패드 내리기
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.editSearchWord.windowToken, 0)
                // 검색
                setResultList()

                return@OnKeyListener true
            }
            false
        })
    }

    private fun setResultList() {
        //var intent = Intent(this, LoadingDialog::class.java)
        //startActivity(intent)
        inputWord = binding.editSearchWord.text.toString()
        if(inputWord.isNotEmpty()){
            cardViewModel.loadSearchResultList(inputWord, this)
            //binding.tvSearchResultDescr.text = "\"$searchWord\"" + getString(R.string.card_search_tv_result)
        }
        cardViewModel.searchResultList.observe(this){
            binding.tvSearchResultDescr.visibility = View.VISIBLE
            binding.tvSearchResultDescr.text = "\"$inputWord\"" + getString(R.string.card_search_tv_result)
            binding.composeViewWordSearchResult.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    LazyColumn {
                        items(it.size) { idx ->
                            SearchResultListItem(
                                searchText = inputWord,
                                title = it[idx].word,
                                descr = it[idx].description,
                                form = it[idx].partsOfSeech,
                                onClick = { moveToDetailActivity(it[idx].word) },
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }
    }

    private fun moveToDetailActivity(searchWord: String) {
        val intent = Intent(this, CardSearchDetailActivity::class.java)
        intent.putExtra(getString(R.string.search_word), searchWord)
        startActivity(intent)
    }
}