package com.example.learn

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import java.lang.ClassCastException
import java.lang.Exception

class CustomInputDialog(val currentContext: Context): AppCompatDialogFragment() {
    private var customInputString:String=""
    private lateinit var listener:CustomInputDialogListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder= AlertDialog.Builder(currentContext)
        val inflater=LayoutInflater.from(currentContext)
        val view=inflater.inflate(R.layout.custom_code_input,null)
        view.findViewById<EditText>(R.id.customInputBox).setText(customInputString)
        builder.setView(view)
            .setTitle("Custom Input")
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->

            })
            .setPositiveButton("Done", DialogInterface.OnClickListener{ dialogInterface, i ->
                customInputString=view.findViewById<EditText>(R.id.customInputBox).text.toString()
                listener.applyText(customInputString)
            })
        return builder.create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try{
            listener=context as CustomInputDialogListener
        }catch (e:ClassCastException){

        }
    }

    public interface CustomInputDialogListener{
        fun applyText(input:String)
    }

}