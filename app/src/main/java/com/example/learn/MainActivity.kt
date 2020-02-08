package com.example.learn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import android.widget.TextView
import android.graphics.PorterDuff
import com.example.learn.Utils.Authenticate
import com.example.learn.Utils.ErrorHandler


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logInButton.setOnClickListener {
            var error = validateInputs()
            if (error.isNotEmpty()) {
                ErrorHandler.handle(this,error)
            } else{
                Authenticate.login(this,username.text.toString(),password.text.toString())
            }

        }
        signUpButton.setOnClickListener {
            var error = validateInputs()
            if (error.isNotEmpty()) {
                ErrorHandler.handle(this,error)
            }
        }
    }

    private fun validateInputs(): String {
        var u=username.text.toString()
        var p=password.text.toString()
        var error= String()
        if(u.isEmpty()){
            error="Please enter username"
        } else if(p.isEmpty()){
            error="Please enter password"
        }
        return error
    }

}
