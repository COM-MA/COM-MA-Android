package com.green.comma.ui.emoji

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.green.comma.R
import com.green.comma.databinding.FragmentEmojiBinding
import com.green.comma.ui.compose.EmojiBottomSheet
import com.green.comma.ui.compose.theme.Lavender500

class EmojiFragment : Fragment() {
    private var _binding: FragmentEmojiBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmojiBinding.inflate(inflater, container, false)
        setBottomSheetDialog()
        setStickerBtn()
        setStickerEmoji()
        setSelectComplete()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun setBottomSheetDialog() {
        EmojiControl.isBottomSheetActive.observe(viewLifecycleOwner){
            binding.composeViewSelectEmoji.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    EmojiBottomSheet(visibility = it, modifier = Modifier)
                }
            }
        }
    }
    private fun setStickerBtn(){
        val selectedBg = R.drawable.shape_stroke_oval_fff3f4ff_lavender200
        val basicBg = R.drawable.shape_stroke_dot_oval_gray100_gray500

        EmojiControl.isChildSelected.observe(viewLifecycleOwner){
            val emojiIdx = EmojiControl.childEmojiIdx.value
            if(emojiIdx != null && emojiIdx > -1){
                binding.btnStickerChild.background = resources.getDrawable(EmojiControl.emojiList[emojiIdx][0])
                binding.btnStickerChild.text = ""
            } else {
                binding.btnStickerChild.background = resources.getDrawable(if(it) selectedBg else basicBg)
                binding.btnStickerChild.text = if(it) "" else "?"
            }
        }

        EmojiControl.isParentsSelected.observe(viewLifecycleOwner){
            val emojiIdx = EmojiControl.parentsEmojiIdx.value
            if(emojiIdx != null && emojiIdx > -1){
                binding.btnStickerParents.background = resources.getDrawable(EmojiControl.emojiList[emojiIdx][0])
                binding.btnStickerParents.text = ""
            } else {
                binding.btnStickerParents.background = resources.getDrawable(if(it) selectedBg else basicBg)
                binding.btnStickerParents.text = if(it) "" else "?"
            }
        }

        binding.btnStickerChild.setOnClickListener {
            EmojiControl.setChildSelected()
        }
        binding.btnStickerParents.setOnClickListener {
            EmojiControl.setParentsSelected()
        }
    }

    private fun setStickerEmoji(){
        EmojiControl.parentsEmojiIdx.observe(viewLifecycleOwner){
            if(it > 0){
                binding.btnStickerParents.background = resources.getDrawable(EmojiControl.emojiList[it][0])
                binding.btnStickerParents.text = ""
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setSelectComplete(){
        EmojiControl.isCompleted.observe(viewLifecycleOwner){
            if(it){
                binding.llEmojiFragment.background = resources.getDrawable(R.drawable.shape_gradient_rect_ffcfd0ff_white)
                binding.llEmojiResult.visibility = View.VISIBLE
                binding.tvSelectDescr.visibility = View.INVISIBLE

                val childIdx = EmojiControl.childEmojiIdx.value
                val parentsIdx = EmojiControl.parentsEmojiIdx.value

                if(childIdx!! > -1 && parentsIdx!! > -1){
                    binding.tvEmojiResult.text = setResultText(childIdx, parentsIdx)
                }
            }
        }
    }

    private fun setResultText(childIdx: Int, parentsIdx: Int): SpannableStringBuilder {
        val strChild = getString(EmojiControl.emojiList[childIdx][2])
        val strParents = getString(EmojiControl.emojiList[parentsIdx][2])
        val resultText = "오늘은\n아이는 $strChild,\n부모는 $strParents 이에요!"

        val childStart = resultText.indexOf(strChild)
        val childEnd = childStart + strChild.length

        val parentsStart = resultText.indexOf(strParents)
        val parentsEnd = parentsStart + strParents.length

        val spannableString = SpannableStringBuilder(resultText)
        spannableString.apply {
            setSpan(ForegroundColorSpan(resources.getColor(R.color.lavender_500)), childStart, childEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(StyleSpan(Typeface.BOLD), childStart, childEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            setSpan(ForegroundColorSpan(resources.getColor(R.color.lavender_500)), parentsStart, parentsEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(StyleSpan(Typeface.BOLD), parentsStart, parentsEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return spannableString
    }
}