package com.example.flowerApplication.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.flowerApplication.R


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navController = Navigation.findNavController(this, R.id.fragment)

    }

    fun changeLanguage(view: View) {
        navController.navigate(R.id.action_main_to_language)
    }

}