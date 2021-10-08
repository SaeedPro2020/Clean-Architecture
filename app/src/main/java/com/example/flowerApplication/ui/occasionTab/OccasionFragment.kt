package com.example.flowerApplication.ui.occasionTab

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.entities.DummyContent
import com.example.flowerApplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class OccasionFragment : Fragment(), MyItemRecyclerViewAdapter.OccasionItemListener,
    MyItemRecyclerViewAdapter.OccasionEditListener {

    private lateinit var myContainer: RelativeLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyItemRecyclerViewAdapter
    private var occasionChanged: Int = 0
    private lateinit var fb: FloatingActionButton

    private lateinit var saveBtn: Button
    private lateinit var cancelBtn: Button
    private lateinit var editTitle: EditText
    private lateinit var editDate: EditText
    private lateinit var layoutAnimation: LinearLayout
    private lateinit var animTransform: Animation
    private val nString = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val view = inflater.inflate(R.layout.fragment_occasion_list, container, false)

        setHasOptionsMenu(true)

        saveBtn = view.findViewById(R.id.saveBtn)
        cancelBtn = view.findViewById(R.id.cancelBtn)
        myContainer = view.findViewById(R.id.myViewContainer)
        editTitle = view.findViewById(R.id.editTitle)
        editDate = view.findViewById(R.id.editDate)
        fb = view.findViewById(R.id.floatingActionButton)

        layoutAnimation = view.findViewById(R.id.linearLayout)
        animTransform = AnimationUtils.loadAnimation(requireContext(), R.anim.fromsmall)

        layoutAnimation.isVisible = false
        layoutAnimation.alpha = 0f

        recyclerView = view.findViewById(R.id.list)

        adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS, this, this)
        recyclerView.adapter = adapter


        cancelBtn.setOnClickListener {

            if (nString == DummyContent.ITEMS[occasionChanged].title) {
                DummyContent.ITEMS.removeAt(occasionChanged)
            }

            recyclerView.isVisible = true
            myContainer.setBackgroundResource(R.color.background_CardView)
            layoutAnimation.isVisible = false

        }

        saveBtn.setOnClickListener {

            DummyContent.ITEMS[occasionChanged].title = editTitle.text.toString()
            DummyContent.ITEMS[occasionChanged].date = editDate.text.toString()

            recyclerView.isVisible = true
            myContainer.setBackgroundResource(R.color.background_CardView)
            layoutAnimation.isVisible = false

            recyclerView.adapter = adapter
        }

        fb.setOnClickListener {
            layoutAnimation.alpha = 1f
            layoutAnimation.startAnimation(animTransform)
            layoutAnimation.isVisible = true

            myContainer.background = screenShot(myContainer)
            recyclerView.isVisible = false

            DummyContent.ITEMS.add(DummyContent.DummyItem("", ""))

            occasionChanged = DummyContent.ITEMS.size - 1


        }

        return view
    }


    override fun onEditBtnClicked(myOccasion: Int) {

        layoutAnimation.alpha = 1f
        layoutAnimation.startAnimation(animTransform)
        layoutAnimation.isVisible = true

        myContainer.background = screenShot(myContainer)
        recyclerView.isVisible = false

        editTitle.setText(DummyContent.ITEMS[myOccasion].title)
        editDate.setText(DummyContent.ITEMS[myOccasion].date)

        occasionChanged = myOccasion

    }

    override fun onDeleteBtnClicked(occasion: Int) {
        DummyContent.ITEMS.removeAt(occasion)
        recyclerView.adapter = adapter
    }

    private fun screenShot(view: View): BitmapDrawable? {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return BitmapDrawable(context?.resources, bitmap)
    }

    companion object {

        // TODO: Customize parameter argument names
        private const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            OccasionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

}
