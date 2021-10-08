@file:Suppress("DEPRECATION")

package com.example.flowerApplication.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.flowerApplication.R
import com.example.flowerApplication.databinding.FragmentDetailBinding
import com.example.flowerApplication.ui.dialogMessage.DialogMessage
import com.example.flowerApplication.viewModel.SharedViewModel

class DetailFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var purchaseBtn: Button
    private lateinit var viewModel: SharedViewModel

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Inflate the layout for this fragment
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        setHasOptionsMenu(true)
        navController = Navigation.findNavController(
            requireActivity(), R.id.fragment
        )

        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)

        val binding = FragmentDetailBinding.inflate(
            inflater, container, false
        )

        purchaseBtn = binding.root.findViewById(R.id.purchaseButton)

        purchaseBtn.setOnClickListener{
            DialogMessage(it)
                .showMessageBox("This is a demo application. This feature is not available")
        }

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

}