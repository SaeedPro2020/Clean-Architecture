package com.example.flowerApplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment


class Languagefragment : Fragment(), View.OnClickListener {

    private lateinit var language1: RadioButton
    private lateinit var language2: RadioButton
    private lateinit var language3: RadioButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.language_fragment, container, false)

        language1 = view.findViewById(R.id.language1)
        language2 = view.findViewById(R.id.language2)
        language3 = view.findViewById(R.id.language3)

        language1.setOnClickListener(this)
        language2.setOnClickListener(this)
        language3.setOnClickListener(this)


        return view
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.language1 -> {
                language2.isChecked = false
                language3.isChecked = false
            }
            R.id.language2 -> {
                language1.isChecked = false
                language3.isChecked = false
            }
            R.id.language3 -> {
                language1.isChecked = false
                language2.isChecked = false
            }
        }
    }

}