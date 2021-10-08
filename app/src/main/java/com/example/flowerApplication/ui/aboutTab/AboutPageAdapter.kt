package com.example.flowerApplication.ui.aboutTab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.entities.AboutDataClass

// 1
@Suppress("DEPRECATION")
class AboutPageAdapter(
    fragmentManager: FragmentManager, private val aboutPages: ArrayList<AboutDataClass>) :
    FragmentStatePagerAdapter(fragmentManager) {

    // 2
    override fun getItem(position: Int): Fragment {
        return AboutFragment.newInstance(aboutPages[position])
    }

    // 3
    override fun getCount(): Int {
        return aboutPages.size
    }

}