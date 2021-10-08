package com.example.flowerApplication.ui.splash

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.flowerApplication.R
import kotlinx.android.synthetic.main.dialog_message_box.view.*


class SplashFragment : Fragment() {

    private lateinit var bottomAnimation: AnimationDrawable
    private lateinit var topAnimation: Animation
    private lateinit var flowerImageView: ImageView
    private lateinit var covereImage: ImageView
    private lateinit var textImageView: ImageView
    private lateinit var myRelative: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        topAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.togo)
        textImageView = view.findViewById(R.id.appNameText)
        flowerImageView = view.findViewById(R.id.flowerImageView)
        covereImage = view.findViewById(R.id.coverImage)
        myRelative = view.findViewById(R.id.container)

        Handler().postDelayed({
            covereImage.isVisible = false
        }, 2000)

        view.post {
            covereImage.startAnimation(topAnimation)

            textImageView.apply {
                setBackgroundResource(R.drawable.animation_list)
                bottomAnimation = background as AnimationDrawable
                bottomAnimation.start()
            }
        }

        if (networkAvailable()) {
            Handler().postDelayed({
                displayMainFragment()
            }, 3000)
        } else {
            Handler().postDelayed({
                showMessageBox("There is a problem with your internet connection")
            }, 3000)
        }

        return view
    }
    private fun displayMainFragment() {
        val navController = Navigation.findNavController(
            requireActivity(),
            R.id.fragment
        )
        navController.navigate(
            R.id.nav_to_mainFragment, null,
            NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build()
        )
    }

    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    private fun showMessageBox(text: String) {

        val messageBoxView =
            LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_message_box, null)
        val messageBoxBuilder = AlertDialog.Builder(requireActivity()).setView(messageBoxView)

        messageBoxView.messageDialog.text = text

        val messageBoxInstance = messageBoxBuilder.show()

        messageBoxView.cancelDialog.setOnClickListener {
            if (!networkAvailable()){
                messageBoxView.messageDialog.text = text
                Toast.makeText(requireContext(),"Your internet connection still has a problem",Toast.LENGTH_LONG).show()
            }else {
                messageBoxInstance.dismiss()
                displayMainFragment()
            }
        }
    }

}