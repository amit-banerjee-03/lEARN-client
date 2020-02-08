package com.example.learn.Utils

import android.content.Context
import android.widget.Toast
import java.lang.Exception

object ErrorHandler{
    fun handle(con:Context,e: String){
        Toast.makeText(con,"Error: $e",Toast.LENGTH_LONG).show()
    }
}