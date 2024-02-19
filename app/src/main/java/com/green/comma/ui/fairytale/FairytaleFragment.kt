package com.green.comma.ui.fairytale

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.green.comma.data.response.fairytale.ResponseFairytaleListDto
import com.green.comma.databinding.FragmentFairytaleBinding
import com.green.comma.ui.compose.FairytaleListItem
import com.green.comma.ui.card.CardDetailActivity
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class FairytaleFragment : Fragment() {

    private var _binding: FragmentFairytaleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val fairytaleViewModel: FairytaleViewModel by viewModels { FairytaleViewModelFactory(requireContext()) }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFairytaleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        fairytaleViewModel.fairytaleItems.observe(viewLifecycleOwner) { it ->
            binding.composeViewFairytale.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    LazyColumn {
                        items(it.size) { index ->
                            FairytaleListItem(data = it[index], { moveToFairytale(it[index]) }, modifier = Modifier)
                        }
                    }
                }
            }
        }
        return root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun moveToFairytale(data: ResponseFairytaleListDto) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val time: LocalTime = LocalTime.parse(data.time, formatter)
        val intent = Intent(activity, FairytaleDetailActivity::class.java)
        intent.putExtra("id", data.id)
        intent.putExtra("title", data.title)
        intent.putExtra("time", time.minute.toString())
        intent.putExtra("videoId", data.link)
        intent.putExtra("subtitleTag", data.subtitleTag)
        intent.putExtra("signTag", data.signTag)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}