@file:Suppress("DEPRECATION")

package com.example.flowerApplication.ui.shopTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.flowerApplication.R
import com.example.flowerApplication.framework.dbModelFlowers.FlowerEntityDb
import com.example.flowerApplication.framework.dbShopList.ShopListEntityDb
import com.example.flowerApplication.viewModel.SharedViewModel

@Suppress("DEPRECATION")
class FlowerFragment(private val MyFlower: FlowerEntityDb) : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val view = inflater.inflate(R.layout.flower_grid_item, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        setHasOptionsMenu(true)

        navController = Navigation.findNavController(
            requireActivity(), R.id.fragment
        )

        val nameText = view.findViewById<TextView>(R.id.nameText)!!
        val textPrice = view.findViewById<TextView>(R.id.priceMain)!!
        val flowerImage = view.findViewById<ImageView>(R.id.flowerImage)!!
        val buyBtn = view.findViewById<Button>(R.id.buyBtnP1)!!
        val addToListBtn = view.findViewById<Button>(R.id.addToListBtn)!!

//        val args = arguments
        nameText.text = MyFlower.flowerName
        textPrice.text = MyFlower.price.toString() + " $"

        Glide.with(requireContext())
            .load(MyFlower.imageFile)
            .into(flowerImage)

        buyBtn.setOnClickListener {
            viewModel.selectedFlower.value = MyFlower
            //navigate to details fragment
            navController.navigate(R.id.action_to_detailFragment)
        }

        addToListBtn.setOnClickListener {
//            insert flowerId in database
            val itemId = MyFlower.flowerId
            val name = MyFlower.flowerName
            val imageFile = MyFlower.imageFile
            val priceOfItem = MyFlower.price
            val itemName = ShopListEntityDb(itemId, name, imageFile, priceOfItem)
            viewModel.shopListItem(itemName)
        }

        return view
    }

    companion object {

        // Method for creating new instances of the fragment
        fun newInstance(flower: FlowerEntityDb): FlowerFragment {
            return FlowerFragment(flower)
        }
    }

}
