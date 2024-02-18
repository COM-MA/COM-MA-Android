package com.green.comma.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.green.comma.MainActivity
import com.green.comma.R
import com.green.comma.databinding.FragmentHomeBinding
import com.green.comma.ui.compose.PreviewFairytaleListItem
import com.green.comma.ui.compose.WordCardListItem
import com.green.comma.ui.card.CardDetailActivity
import com.green.comma.ui.card.CardViewModel
import com.green.comma.ui.card.CardViewModelFactory
import com.green.comma.ui.fairytale.FairytaleViewModel
import com.green.comma.ui.fairytale.FairytaleViewModelFactory
import com.green.comma.util.BottomNavigationHelper


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val cardViewModel: CardViewModel by viewModels { CardViewModelFactory(requireContext()) }
    private val fairytaleViewModel: FairytaleViewModel by viewModels { FairytaleViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setWordCardPreviewList()
        setFairytalePreviewList()

        val textView: TextView = binding.textHome
        userViewModel.text.observe(viewLifecycleOwner) {
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

    override fun onStart() {
        super.onStart()
        Log.d("FRAGMENT STATUS", "onStart")
        updateStatusBarColor(true, R.color.lavender_500)
    }

    override fun onStop() {
        super.onStop()
        Log.d("FRAGMENT STATUS", "onStop")
        updateStatusBarColor(false, R.color.white)
    }

    private fun setWordCardPreviewList(){
        val includeWordCardBinding = binding.includeHomeWordCard

        cardViewModel.items.observe(viewLifecycleOwner) { it ->
            println("data changed")
            includeWordCardBinding.composeViewHomePreviewList.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                println("compose changed1")
                var itemCount = if(it.size < 5) it.size else 5
                setContent {
                    LazyRow(modifier = Modifier.padding(start = 20.dp)){
                        items(itemCount) { item ->
                            WordCardListItem(it[item], { moveToCardDetail() })
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

        includeWordCardBinding.btnMore.setOnClickListener{
            val mainActivity = activity as MainActivity
            BottomNavigationHelper.triggerMenuItemSelected(bottomNavigationView = mainActivity.getBottomNavigationView(), R.id.navigation_card)
        }
    }

    private fun setFairytalePreviewList(){
        val includeFairytaleBinding = binding.includeHomePopularFairytale
        fairytaleViewModel.items.observe(viewLifecycleOwner) { it ->
            includeFairytaleBinding.composeViewHomePreviewList.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                var itemCount = if(it.size < 4) it.size else 4
                setContent {
                    LazyRow(modifier = Modifier.padding(start = 20.dp)) {
                        items(itemCount) { item ->
                            PreviewFairytaleListItem(it[item].imgaeUrl)
                        }
                    }
                    DisposableEffect(Unit) {
                        fairytaleViewModel.loadFairytaleList()
                        onDispose {}
                    }
                }
            }
        }

        includeFairytaleBinding.btnMore.setOnClickListener{
            val mainActivity = activity as MainActivity
            BottomNavigationHelper.triggerMenuItemSelected(bottomNavigationView = mainActivity.getBottomNavigationView(), R.id.navigation_content)
        }
    }

    private fun updateStatusBarColor(isBright: Boolean, color: Int) { // Color must be in hexadecimal fromat
        val mWindow = requireActivity().window
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        mWindow.statusBarColor = ContextCompat.getColor(requireActivity(), color)
        if(Build.VERSION.SDK_INT >= 23){
            if(isBright) {
                mWindow.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}