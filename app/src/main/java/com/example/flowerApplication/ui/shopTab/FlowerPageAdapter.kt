package com.example.flowerApplication.ui.shopTab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.flowerApplication.framework.dbModelFlowers.FlowerEntityDb

// 1
@Suppress("DEPRECATION")
class FlowerPageAdapter(fragmentManager: FragmentManager, private val flower: List<FlowerEntityDb>) :
    FragmentStatePagerAdapter(fragmentManager) {

    // 2
    override fun getItem(position: Int): Fragment {
        return FlowerFragment.newInstance(flower[position])
    }

    // 3
    override fun getCount(): Int {
        return flower.size
    }
}