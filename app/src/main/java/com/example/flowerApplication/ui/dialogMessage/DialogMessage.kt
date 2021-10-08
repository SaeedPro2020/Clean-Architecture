package com.example.flowerApplication.ui.dialogMessage

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.flowerApplication.R
import kotlinx.android.synthetic.main.dialog_message_box.view.*

class DialogMessage(view: View) {
    private val myView =  view

    fun showMessageBox(text: String) {

        //Inflate the dialog as custom view
        val messageBoxView =
            LayoutInflater.from(myView.context).inflate(R.layout.dialog_message_box, null)

        //AlertDialogBuilder
        val messageBoxBuilder = AlertDialog.Builder(myView.context).setView(messageBoxView)

        //setting text values
        messageBoxView.messageDialog.text = text

        //show dialog
        val messageBoxInstance = messageBoxBuilder.show()

        //set Listener
        messageBoxView.cancelDialog.setOnClickListener {
            //close dialog
            messageBoxInstance.dismiss()
        }
    }

}