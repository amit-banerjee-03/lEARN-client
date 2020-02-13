package com.example.learn

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import android.widget.TextView
import android.graphics.PorterDuff
import com.example.learn.Utils.Authenticate
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.ErrorHandler


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jwt=AuthenticationToken(this).getJWT()
        if(jwt==""){
            Toast.makeText(this,"User not logged in",Toast.LENGTH_LONG).show()
        } else{
            Toast.makeText(this,"User logged in",Toast.LENGTH_LONG).show()
//            intent = Intent(this,Home::class.java)
//            startActivity(intent)
//            finish()
        }
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
        return error
        if(u.isEmpty()){
            error="Please enter username"
        } else if(p.isEmpty()){
            error="Please enter password"
        }
        return error
    }

}
