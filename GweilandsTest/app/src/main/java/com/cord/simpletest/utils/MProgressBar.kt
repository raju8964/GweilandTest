package com.cord.simpletest.utils
import android.app.Activity
import android.app.Dialog

import android.view.Window
import android.widget.LinearLayout.LayoutParams
import com.cord.simpletest.R
import com.cord.simpletest.databinding.ProgressDialogUiBinding
class MProgressBar {
    fun showDialog(activity: Activity?):Dialog {
        val dialog = Dialog(activity!!, R.style.DialogStyle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        var binding= ProgressDialogUiBinding.inflate(activity!!.layoutInflater)
        dialog.window?.setLayout(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        dialog.setContentView(binding.root)
        //dialog.setCancelable(true)
        dialog.setOnDismissListener {
            dialog.dismiss()
        }
        return dialog
    }
}