package com.green.comma.ui.content

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.green.comma.databinding.FragmentContentBinding
import com.green.comma.ui.compose.FairytaleListItem
import com.green.comma.ui.card.CardDetailActivity

class ContentFragment : Fragment() {

    private var _binding: FragmentContentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userViewModel =
            ViewModelProvider(this).get(ContentViewModel::class.java)

        _binding = FragmentContentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.composeViewFairytale.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LazyColumn{
                    items(10) {index ->
                        FairytaleListItem(modifier = Modifier)
                    }
                }
            }
        }

        val textView: TextView = binding.textNotifications
        userViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
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