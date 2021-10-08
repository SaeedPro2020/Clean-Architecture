@file:Suppress("DEPRECATION")

package com.example.flowerApplication.ui.shopListTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.flowerApplication.R
import com.example.flowerApplication.ui.dialogMessage.DialogMessage
import com.example.flowerApplication.viewModel.SharedViewModel

@Suppress("DEPRECATION")
class ShopListFragment : Fragment(), ShopItemsRecyclerAdapter.ShopItemListener,
    ShopItemsRecyclerAdapter.SelectedItemListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var adapterForItems: ShopItemsRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var clearList: Button
    private lateinit var paymentBtn: Button
    private lateinit var totalPrice: TextView
    private lateinit var countItem: TextView
    var totalItemPrice: Float = 0f
    private var totalItem: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val view = inflater.inflate(R.layout.fragment_shop_list, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        viewModel.refreshShopList()

        setHasOptionsMenu(true)

        recyclerView = view.findViewById(R.id.listOfShopItems)
        clearList = view.findViewById(R.id.clearListBtn)
        paymentBtn = view.findViewById(R.id.paymentButton)
        totalPrice = view.findViewById(R.id.totalPriceAll)
        countItem = view.findViewById(R.id.totalItems)

        refreshView()

        clearList.setOnClickListener {
            recyclerView.adapter = null
            totalPrice.text = "Total price: 0 $"
            countItem.text = "Total item: 0"
            viewModel.deleteDataBase()
        }

        paymentBtn.setOnClickListener{
            DialogMessage(view)
                .showMessageBox("This is a demo application. This feature is not available")
        }

        return view
    }

    private fun refreshView() {
        viewModel.shopListItems.observe(viewLifecycleOwner, {
            adapterForItems = ShopItemsRecyclerAdapter(
                requireContext(),
                it, this, this
            )
            recyclerView.adapter = adapterForItems

            it.forEach {
                totalItem += it.price
                totalItemPrice = totalItem.toFloat()
            }
            countItem.text = "Total item: " + adapterForItems.itemCount.toString()
            viewModel.removePrice(totalItemPrice, 0f)

        })

        viewModel.newValue.observe(viewLifecycleOwner, {
            totalPrice.text = "Total price: $it $"
            totalItemPrice = it
            totalItem = 0.0
        })
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ShopListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    override fun onRemoveBtnClicked(item: Int) {
        viewModel.removeItemFromDb(item)
        viewModel.refreshShopList()
    }

    override fun onUpdatePrice(item: Float, operation: String) {
        if (operation == "add") {
            viewModel.addPrice(totalItemPrice, item)
        } else {
            viewModel.removePrice(totalItemPrice, item)
        }
    }
}
