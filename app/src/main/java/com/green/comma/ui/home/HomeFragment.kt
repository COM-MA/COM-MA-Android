package com.green.comma.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.AppBarLayout
import com.green.comma.CommaApplication
import com.green.comma.MainActivity
import com.green.comma.R
import com.green.comma.databinding.FragmentHomeBinding
import com.green.comma.ui.compose.PreviewFairytaleListItem
import com.green.comma.ui.compose.WordCardListItem
import com.green.comma.ui.card.CardDetailActivity
import com.green.comma.util.BottomNavigationHelper


class HomeFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {

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
        homeViewModel.loadHomeData()

        initViewPager()

        setWordCardPreviewList()
        setFairytalePreviewList()
        setSwipeRefresh()

        return root
    }


    private fun initViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(requireActivity(), homeViewModel)
        binding.viewpager.adapter = viewPagerAdapter
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        binding.swipeRefreshHome.isEnabled = verticalOffset == 0
    }

    override fun onResume() {
        super.onResume()
        binding.appbarHome.addOnOffsetChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        binding.appbarHome.removeOnOffsetChangedListener(this)
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
        updateStatusBarColor(true, R.color.lavender_500)
    }

    override fun onStop() {
        super.onStop()
        updateStatusBarColor(false, R.color.white)
    }

    private fun setWordCardPreviewList(){
        val includeWordCardBinding = binding.includeHomeWordCard

        homeViewModel.homeDataItem.observe(viewLifecycleOwner) { it ->
            val itemCount = if(it.top5Cards.size < 5) it.top5Cards.size else 5
            if(it.top5Cards.isNotEmpty()) includeWordCardBinding.composeViewHomePreviewList.background = null
            else includeWordCardBinding.composeViewHomePreviewList.setBackgroundResource(R.drawable.img_bg_no_card)
            includeWordCardBinding.composeViewHomePreviewList.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    LazyRow(modifier = Modifier.padding(start = 20.dp)){
                        items(itemCount) { item ->
                            WordCardListItem(it.top5Cards[item], { moveToCardDetail(it.top5Cards[item].userCardId) }, false)
                        }
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
        includeFairytaleBinding.composeViewHomePreviewList.background = null
        homeViewModel.homeDataItem.observe(viewLifecycleOwner) { it ->
            val itemCount = if(it.top2Fairytales.size < 2) it.top2Fairytales.size else 2
            includeFairytaleBinding.composeViewHomePreviewList.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    LazyRow(modifier = Modifier.padding(start = 30.dp)) {
                        items(itemCount) { item ->
                            PreviewFairytaleListItem(it.top2Fairytales[item].recommendImageUrl, 300.dp, 20.dp)
                        }
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

    private fun setSwipeRefresh(){
        binding.swipeRefreshHome.setOnRefreshListener {
            homeViewModel.loadHomeData()
            binding.swipeRefreshHome.isRefreshing = false
        }
    }
}