package com.example.learn.Utils

import android.content.Context
import android.widget.Toast
import com.example.learn.R
import java.lang.Exception

object ErrorHandler{
    fun handle(con:Context,e: String){
        val toast=Toast.makeText(con,"\tError: $e ",Toast.LENGTH_LONG)
        val view=toast.view
        view.setBackgroundResource(R.mipmap.red_bar)
        toast.show()
    }
}