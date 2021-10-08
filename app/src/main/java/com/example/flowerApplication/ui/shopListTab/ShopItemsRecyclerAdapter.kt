package com.example.flowerApplication.ui.shopListTab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flowerApplication.R
import com.example.flowerApplication.framework.dbShopList.ShopListEntityDb
import kotlinx.android.synthetic.main.fragment_shop_item.view.*


class ShopItemsRecyclerAdapter(
    private val context: Context,
    private val values: List<ShopListEntityDb>,
    private val removeItemListener: ShopItemListener,
    private val priceItemListener: SelectedItemListener
) : RecyclerView.Adapter<ShopItemsRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_shop_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopItem = values[position]
        with(holder) {
            itemTextView.text = shopItem.itemName
            itemTextPrice.text = shopItem.price.toString() + "$"

            Glide.with(context)
                .load(shopItem.imageFile)
                .into(imageOfItem)

            removeItemBtn.setOnClickListener{
                removeItemListener.onRemoveBtnClicked(shopItem.itemId)
            }

            selectItemBtn.setOnClickListener {
                if(it.checkBoxItem.isChecked){
                    priceItemListener.onUpdatePrice(shopItem.price.toFloat(), "add")
                }else{
                    priceItemListener.onUpdatePrice(shopItem.price.toFloat(), "minus")
                }
            }

        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val itemTextView: TextView = view.findViewById(R.id.itemForShopping)
        val itemTextPrice: TextView = view.findViewById(R.id.textprice)
        val imageOfItem: ImageView = view.findViewById(R.id.imageForList)
        val removeItemBtn: Button = view.findViewById(R.id.imageforRemove)
        val selectItemBtn: Button = view.findViewById(R.id.checkBoxItem)

    }

    interface ShopItemListener{
        fun onRemoveBtnClicked(item: Int)
    }

    interface SelectedItemListener{
        fun onUpdatePrice(item: Float, opreration: String)
    }

}