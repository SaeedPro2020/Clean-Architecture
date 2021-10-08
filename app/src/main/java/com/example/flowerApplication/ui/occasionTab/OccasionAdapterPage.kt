package com.example.flowerApplication.ui.occasionTab

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.entities.OccasionDataClass


@Suppress("DEPRECATION")
class OccasionAdapterPage(
    manager: FragmentManager,
    private val occasion: ArrayList<OccasionDataClass>
) :
    FragmentStatePagerAdapter(manager) {

    @NonNull
    override fun getItem(position: Int): Fragment {
        return OccasionFragment.newInstance(1)
    }

    override fun getCount(): Int {
        return occasion.size
    }
}