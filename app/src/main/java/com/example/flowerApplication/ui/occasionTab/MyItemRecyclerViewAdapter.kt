package com.example.flowerApplication.ui.occasionTab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.entities.DummyContent
import com.example.flowerApplication.R

class MyItemRecyclerViewAdapter(
    private val values: List<DummyContent.DummyItem>,
    private val itemListener: OccasionItemListener,
    private val editBtnListener: OccasionEditListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_occasion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        with(holder) {
            idView.text = item.title
            contentView.text = item.date

            deleteButton.setOnClickListener{
                itemListener.onDeleteBtnClicked(position)
            }

            editButton.setOnClickListener {
                editBtnListener.onEditBtnClicked(position)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        val deleteButton: Button = view.findViewById(R.id.imageFordelete)
        val editButton: Button = view.findViewById(R.id.ForEditBtn)

    }

    interface OccasionItemListener {
        fun onDeleteBtnClicked(occasion: Int)
    }

    interface OccasionEditListener {
        fun onEditBtnClicked(myOccasion: Int)
    }
}