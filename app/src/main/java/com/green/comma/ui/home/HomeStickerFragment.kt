package com.green.comma.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.green.comma.CommaApplication
import com.green.comma.R
import com.green.comma.databinding.FragmentCardBinding
import com.green.comma.databinding.FragmentHomeStickerBinding

class HomeStickerFragment(homeViewModel: HomeViewModel) : Fragment() {
    // TODO: Rename and change types of parameters
    private val homeViewModel = homeViewModel
    private var _binding: FragmentHomeStickerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeStickerBinding.inflate(inflater, container, false)
        setEvent()
        setNickname()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setEvent() {
        homeViewModel.homeDataItem.observe(viewLifecycleOwner) { it ->
            binding.includeStickerAttend.imgSticker.visibility = View.VISIBLE

            binding.includeStickerWord.textView.elevation = if (it.home.isWordRegistered) 0f else 8f
            binding.includeStickerWord.imgSticker.visibility =
                if (it.home.isWordRegistered) View.VISIBLE else View.INVISIBLE

            binding.includeStickerQuiz.textView.elevation =
                if (it.home.isQuizParticipated) 0f else 8f
            binding.includeStickerQuiz.imgSticker.visibility =
                if (it.home.isQuizParticipated) View.VISIBLE else View.INVISIBLE

            binding.includeStickerFairytale.textView.elevation =
                if (it.home.isFairyTalePlayed) 0f else 8f
            binding.includeStickerFairytale.imgSticker.visibility =
                if (it.home.isFairyTalePlayed) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun setNickname(){
        val savedNickname = CommaApplication.preferences.getString(getString(R.string.nickname), "")
        println(savedNickname)
        binding.textGreeting.text = savedNickname + getString(R.string.home_tv_greeting)
        println(binding.textGreeting.text)
    }
}