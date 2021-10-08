@file:Suppress("DEPRECATION")

package com.example.flowerApplication.ui.aboutTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.entities.AboutDataClass
import com.example.flowerApplication.R
import com.example.flowerApplication.framework.dbModelAbout.imageAbout
import com.example.flowerApplication.viewModel.SharedViewModel

@Suppress("DEPRECATION")
class AboutFragment : Fragment(){

    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val view = inflater.inflate(R.layout.fragment_about_items, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)

        setHasOptionsMenu(true)

        val pageImage = view.findViewById<ImageView>(R.id.aboutImageView)!!
        val pageTitle = view.findViewById<TextView>(R.id.titleAbout)!!
        val pageDescription = view.findViewById<TextView>(R.id.descriptionAbout)!!

        val args = arguments
        pageDescription.text = args?.getString("pageDescription")
        pageTitle.text = args?.getString("title")
        pageImage.setImageResource(imageAbout[args?.getInt("pageNumber")]!!)

        return view
    }


    companion object {

        // Method for creating new instances of the fragment
        fun newInstance(about: AboutDataClass): AboutFragment {

            val args = Bundle()
            args.putInt("pageNumber", about.page)
            args.putString("pageDescription", about.description)
            args.putString("title", about.title)

            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }

}