package com.green.comma.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.green.comma.MainActivity
import com.green.comma.R
import com.green.comma.databinding.FragmentHomeEmojiBinding
import com.green.comma.ui.emoji.EmojiControl
import com.green.comma.ui.emoji.EmojiViewModel
import com.green.comma.ui.emoji.EmojiViewModelFactory
import com.green.comma.util.BottomNavigationHelper

class HomeEmojiFragment : Fragment() {


    private var _binding: FragmentHomeEmojiBinding? = null
    private val binding get() = _binding!!
    private val emojiViewModel: EmojiViewModel by viewModels { EmojiViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeEmojiBinding.inflate(inflater, container, false)
        emojiViewModel.loadEmoji()
        setEmoji()
        setEnrollBtn()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        emojiViewModel.loadEmoji()
    }

    private fun setEmoji() {
        val emojiMap = mapOf(
            getString(R.string.today_feeling_emoji_title_soso) to 0,
            getString(R.string.today_feeling_emoji_title_mad) to 1,
            getString(R.string.today_feeling_emoji_title_sad) to 2,
            getString(R.string.today_feeling_emoji_title_calm) to 3,
            getString(R.string.today_feeling_emoji_title_depressed) to 4,
            getString(R.string.today_feeling_emoji_title_happy) to 5,
            getString(R.string.today_feeling_emoji_title_proud) to 6,
            getString(R.string.today_feeling_emoji_title_anxious) to 7,
            getString(R.string.today_feeling_emoji_title_idontknow) to 8,
        )
        emojiViewModel.emojiData.observe(viewLifecycleOwner) { it ->
            if(it != null){
                if(emojiMap[it.childEmotion] != null && emojiMap[it.parentEmotion] != null) {
                    binding.btnStickerChild.background =
                        resources.getDrawable(EmojiControl.emojiList[emojiMap[it.childEmotion]!!].img)
                    binding.btnStickerParents.background =
                        resources.getDrawable(EmojiControl.emojiList[emojiMap[it.parentEmotion]!!].img)
                    binding.btnStickerChild.text = ""
                    binding.btnStickerParents.text = ""
                }
            } else {
                binding.btnStickerChild.background =
                    resources.getDrawable(R.drawable.shape_stroke_dot_oval_gray100_gray500)
                binding.btnStickerParents.background =
                    resources.getDrawable(R.drawable.shape_stroke_dot_oval_gray100_gray500)
                binding.btnStickerChild.text = "?"
                binding.btnStickerParents.text = "?"
            }
        }
    }

    private fun setEnrollBtn(){
        binding.frameSticker.setOnClickListener {
            val mainActivity = activity as MainActivity
            BottomNavigationHelper.triggerMenuItemSelected(bottomNavigationView = mainActivity.getBottomNavigationView(), R.id.navigation_emoji)
        }
    }
}