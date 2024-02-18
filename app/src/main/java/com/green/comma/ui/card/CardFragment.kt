package com.green.comma.ui.card

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.green.comma.R
import com.green.comma.databinding.FragmentCardBinding
import com.green.comma.ui.compose.WordCardListItem

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val cardViewModel: CardViewModel by viewModels { ViewModelFactory(requireContext()) }

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

        _binding = FragmentCardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        var columCount = 2

        cardViewModel.items.observe(viewLifecycleOwner) { it ->
            binding.composeViewWordCard.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    LazyVerticalGrid(
                        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding)),
                        columns = GridCells.Fixed(columCount)
                    ) {
                        items(it.size) { item ->
                            WordCardListItem(it[item].cardImageUrl, { moveToCardDetail() })
                        }
                    }
                    DisposableEffect(Unit) {
                        cardViewModel.loadLatestCardList()
                        onDispose {}
                    }
                }
            }
        }

        val textView: TextView = binding.textDashboard
        cardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    private fun moveToCardDetail() {
        val intent = Intent(activity, CardDetailActivity::class.java) //fragment라서 activity intent와는 다른 방식
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}