package com.green.comma.ui.card

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.dimensionResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.green.comma.R
import com.green.comma.databinding.FragmentCardBinding
import com.green.comma.ui.compose.WordCardListItem

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val cardViewModel: CardViewModel by viewModels { CardViewModelFactory(requireContext()) }
    private val columCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardViewModel.loadLatestCardList()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)

        setCardList()
        setCardAlignBtn()
        setSelectBtn()
        setSwipeRefresh()

        return binding.root
    }

    private fun setCardList(){
        cardViewModel.cardListItems.observe(viewLifecycleOwner) { it ->
            binding.composeViewWordCard.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    LazyVerticalGrid(
                        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding)),
                        columns = GridCells.Fixed(columCount)
                    ) {
                        items(it.size) { item ->
                            WordCardListItem(it[item], { moveToCardDetail(it[item].userCardId) }, false)
                        }
                    }
                }
            }
        }
    }
    private fun setCardAlignBtn(){
        val tvCardAlignType = binding.tvCardAlignType
        val alphaTypeStr = getString(R.string.card_tv_align_alphabetical)
        val latestTypeStr = getString(R.string.card_tv_align_latest)
        binding.btnCardAlign.setOnClickListener {
            if(tvCardAlignType.text == alphaTypeStr){
                tvCardAlignType.text = latestTypeStr
                cardViewModel.loadLatestCardList()
            } else {
                tvCardAlignType.text = alphaTypeStr
                cardViewModel.loadAlphabetCardList()
            }
        }
    }
    private fun setSelectBtn(){
        binding.includeCardToolbar.btnText.setOnClickListener {
            activity?.let{
                val intent = Intent(context, SelectCardActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun moveToCardDetail(userCardId: Long) {
        val intent = Intent(activity, CardDetailActivity::class.java)
        intent.putExtra("userCardId", userCardId)
        startActivity(intent)
    }
    private fun setSwipeRefresh(){
        binding.swipeRefreshCardList.setOnRefreshListener {
            cardViewModel.loadLatestCardList()
            binding.swipeRefreshCardList.isRefreshing = false
        }
    }
}