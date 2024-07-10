package com.srm.androidtendable.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogUtil {
    fun showDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Tendable")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}