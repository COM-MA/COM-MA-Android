package com.green.comma.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity, homeViewModel: HomeViewModel) :
    FragmentStateAdapter(fragmentActivity) {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val homeEmojiFragment = HomeEmojiFragment()
    private val fragments = listOf<Fragment>(HomeStickerFragment(homeViewModel), homeEmojiFragment)

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}