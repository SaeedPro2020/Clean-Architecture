package com.example.flowerApplication.ui.shopListTab

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.flowerApplication.framework.dbShopList.ShopListEntityDb


@Suppress("DEPRECATION")
class ShopAdapterPage(
    manager: FragmentManager,
    private val itemList: ArrayList<ShopListEntityDb>
) :
    FragmentStatePagerAdapter(manager) {

    @NonNull
    override fun getItem(position: Int): Fragment {
        return ShopListFragment.newInstance(1)
    }

    override fun getCount(): Int {
        return itemList.size
    }
}