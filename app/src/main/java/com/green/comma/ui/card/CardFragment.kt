package com.green.comma.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.green.comma.R
import com.green.comma.databinding.FragmentCardBinding
import com.green.comma.ui.WordCardListItem

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cardViewModel =
            ViewModelProvider(this).get(CardViewModel::class.java)

        _binding = FragmentCardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.composeViewWordCard.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LazyVerticalGrid(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding)), columns = GridCells.Fixed(2)){
                    items(10) {index ->
                        WordCardListItem(painterResource(id = R.drawable.ic_bottom_nav_camera))
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}