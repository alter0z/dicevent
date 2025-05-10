package com.ansorisan.dicevent.base.utils.dialogues

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AlertDialog(activity: AppCompatActivity) {
    private var builder: MaterialAlertDialogBuilder? = null
    init {
        builder = MaterialAlertDialogBuilder(activity)
    }

    fun successAlert(message: String) {
        builder?.setTitle("Success!")
        builder?.setMessage(message)
        builder?.setNeutralButton("Close") { _, _ -> }
        val dialog = builder?.create()
        dialog?.show()
    }

    fun warningAlert(message: String) {
        builder?.setTitle("Warning!")
        builder?.setMessage(message)
        builder?.setNeutralButton("Close") { _, _ -> }
        val dialog = builder?.create()
        dialog?.show()
    }

    fun errorAlert(message: String) {
        builder?.setTitle("Error!")
        builder?.setMessage(message)
        builder?.setNeutralButton("Close") { _, _ -> }
        val dialog = builder?.create()
        dialog?.show()
    }

//    fun successWithCallback(message: String, callback: (Boolean) -> Unit) {
//        builder?.setTitle("Success!")
//        builder?.setMessage(message)
//        builder?.setNeutralButton("Close") { _, _ -> callback(true) }
//
//        val dialog = builder?.create()
//        dialog?.show()
//    }
}