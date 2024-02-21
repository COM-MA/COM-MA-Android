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
import com.green.comma.MainActivity
import com.green.comma.R
import com.green.comma.databinding.FragmentHomeBinding
import com.green.comma.ui.compose.PreviewFairytaleListItem
import com.green.comma.ui.compose.WordCardListItem
import com.green.comma.ui.card.CardDetailActivity
import com.green.comma.util.BottomNavigationHelper


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setEvent()
        setWordCardPreviewList()
        setFairytalePreviewList()

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
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

    private fun setEvent() {
        homeViewModel.homeDataItem.observe(viewLifecycleOwner) { it ->
            binding.includeStickerAttend.imgSticker.visibility = View.VISIBLE

            binding.includeStickerWord.textView.elevation = if(it.home.isWordRegistered) 0f else 8f
            binding.includeStickerWord.imgSticker.visibility = if(it.home.isWordRegistered) View.VISIBLE else View.INVISIBLE

            binding.includeStickerQuiz.textView.elevation = if(it.home.isQuizParticipated) 0f else 8f
            binding.includeStickerQuiz.imgSticker.visibility = if(it.home.isQuizParticipated) View.VISIBLE else View.INVISIBLE

            binding.includeStickerFairytale.textView.elevation = if(it.home.isFairyTalePlayed) 0f else 8f
            binding.includeStickerFairytale.imgSticker.visibility = if(it.home.isFairyTalePlayed) View.VISIBLE else View.INVISIBLE

        }
    }

    private fun setWordCardPreviewList(){
        val includeWordCardBinding = binding.includeHomeWordCard

        homeViewModel.homeDataItem.observe(viewLifecycleOwner) { it ->
            val itemCount = 5
            includeWordCardBinding.composeViewHomePreviewList.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    LazyRow(modifier = Modifier.padding(start = 20.dp)){
                        items(itemCount) { item ->
                            WordCardListItem(it.top5Cards[item], { moveToCardDetail(it.top5Cards[item].userCardId) }, false)
                        }
                    }
                    DisposableEffect(Unit) {
                        homeViewModel.loadHomeData()
                        onDispose {}
                    }
                }
            }
        }

        includeWordCardBinding.btnMore.setOnClickListener{
            val mainActivity = activity as MainActivity
            BottomNavigationHelper.triggerMenuItemSelected(bottomNavigationView = mainActivity.getBottomNavigationView(), R.id.navigation_card)
        }
    }

    private fun setFairytalePreviewList(){
        val includeFairytaleBinding = binding.includeHomePopularFairytale
        homeViewModel.homeDataItem.observe(viewLifecycleOwner) { it ->
            includeFairytaleBinding.composeViewHomePreviewList.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                var itemCount = 2
                setContent {
                    LazyRow(modifier = Modifier.padding(start = 30.dp)) {
                        items(itemCount) { item ->
                            PreviewFairytaleListItem(it.top2Fairytales[item].recommendImageUrl, 300.dp, 20.dp)
                        }
                    }
                    DisposableEffect(Unit) {
                        homeViewModel.loadHomeData()
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