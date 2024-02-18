package com.green.comma.ui.fairytale

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.green.comma.databinding.FragmentFairytaleBinding
import com.green.comma.ui.compose.FairytaleListItem
import com.green.comma.ui.card.CardDetailActivity

class FairytaleFragment : Fragment() {

    private var _binding: FragmentFairytaleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val fairytaleViewModel: FairytaleViewModel by viewModels { FairytaleViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFairytaleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        fairytaleViewModel.items.observe(viewLifecycleOwner) { it ->
            binding.composeViewFairytale.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    LazyColumn {
                        items(it.size) { index ->
                            FairytaleListItem(data = it[index], modifier = Modifier)
                        }
                    }
                }
            }
        }
        return root
    }

    private fun moveToCardDetail() {
        val intent = Intent(activity, CardDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}