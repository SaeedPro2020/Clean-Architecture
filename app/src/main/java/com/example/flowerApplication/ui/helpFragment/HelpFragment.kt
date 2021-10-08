package com.example.flowerApplication.ui.helpFragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.flowerApplication.R

class HelpFragment : Fragment(), View.OnClickListener {

    private lateinit var btnChat : Button
    private lateinit var btnCall : Button
    private lateinit var btnEmail : Button
    private lateinit var btnRead : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)

        btnChat = view.findViewById(R.id.btnChat)
        btnCall = view.findViewById(R.id.btnCall)
        btnEmail = view.findViewById(R.id.btnEmail)
        btnRead = view.findViewById(R.id.btnRead)

        btnChat.setOnClickListener(this)
        btnCall.setOnClickListener(this)
        btnEmail.setOnClickListener(this)
        btnRead.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.btnChat -> {
                btnChat.setBackgroundResource(R.drawable.background_btn2_help_fragment)
                btnCall.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnEmail.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnRead.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnChat.setTextColor(Color.parseColor("#FFFFFFFF"))
            }
            R.id.btnCall -> {
                btnChat.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnCall.setBackgroundResource(R.drawable.background_btn2_help_fragment)
                btnEmail.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnRead.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnCall.setTextColor(Color.parseColor("#FFFFFFFF"))

            }
            R.id.btnEmail -> {
                btnChat.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnCall.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnEmail.setBackgroundResource(R.drawable.background_btn2_help_fragment)
                btnRead.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnEmail.setTextColor(Color.parseColor("#FFFFFFFF"))

            }
            R.id.btnRead -> {
                btnChat.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnCall.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnEmail.setBackgroundResource(R.drawable.background_btn1_help_fragment)
                btnRead.setBackgroundResource(R.drawable.background_btn2_help_fragment)
                btnRead.setTextColor(Color.parseColor("#FFFFFFFF"))

            }
        }
    }
}