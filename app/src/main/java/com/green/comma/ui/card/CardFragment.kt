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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val cardViewModel: CardViewModel by viewModels { ViewModelFactory(requireContext()) }

        /*viewModel.viewModelScope.launch {
            delay(Companion.debounceTime)
            input?.toString()?.let { viewModel.getUniversityList(it) }
        }*/

        //cardViewModel.loadLatestCardList()

        _binding = FragmentCardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        var columCount = 2
        val alphaTypeStr = getString(R.string.card_tv_align_alphabetical)
        val latestTypeStr = getString(R.string.card_tv_align_latest)
        val tvCardAlignType = binding.tvCardAlignType

        cardViewModel.cardListItems.observe(viewLifecycleOwner) { it ->
            println("data changed")
            binding.composeViewWordCard.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                println("compose changed1")
                setContent {
                    LazyVerticalGrid(
                        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding)),
                        columns = GridCells.Fixed(columCount)
                    ) {
                        items(it.size) { item ->
                            WordCardListItem(it[item], { moveToCardDetail(it[item].userCardId) })
                        }
                    }
                    DisposableEffect(Unit) {
                        cardViewModel.loadLatestCardList()
                        onDispose {}
                    }
                }
                println("compose changed2")
            }
        }

        binding.btnCardAlign.setOnClickListener {
            if(tvCardAlignType.text == alphaTypeStr){
                println("알파벳순")
                tvCardAlignType.text = latestTypeStr
                cardViewModel.loadAlphabetCardList()
            } else {
                println("가나다순")
                tvCardAlignType.text = alphaTypeStr
                cardViewModel.loadLatestCardList()
            }
        }

        return root
    }

    private fun moveToCardDetail(userCardId: Long) {
        val intent = Intent(activity, CardDetailActivity::class.java)
        intent.putExtra("userCardId", userCardId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}