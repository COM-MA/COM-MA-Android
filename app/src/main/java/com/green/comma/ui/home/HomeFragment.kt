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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    private fun setWordCardPreviewList(){
        val includeWordCardBinding = binding.includeHomeWordCard

        includeWordCardBinding.composeViewHomePreviewList.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LazyRow(modifier = Modifier.padding(start = 20.dp)){
                    items(10) {index ->
                        WordCardListItem(painterResource(id = R.drawable.ic_bottom_nav_camera), { moveToCardDetail() })
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

        includeFairytaleBinding.composeViewHomePreviewList.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LazyRow(modifier = Modifier.padding(start = 20.dp)){
                    items(5) {index ->
                        PreviewFairytaleListItem(painterResource(id = R.drawable.ic_bottom_nav_card_fill))
                    }
                }
            }
        }

        includeFairytaleBinding.btnMore.setOnClickListener{
            val mainActivity = activity as MainActivity
            BottomNavigationHelper.triggerMenuItemSelected(bottomNavigationView = mainActivity.getBottomNavigationView(), R.id.navigation_content)
        }
    }
}