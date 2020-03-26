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
import android.view.Menu
import android.view.MenuItem
import com.example.learn.Menu.Action
import com.example.learn.Utils.Authenticate
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.ErrorHandler


class MainActivity : AppCompatActivity() {

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        setContentView(R.layout.activity_main)
        val jwt=AuthenticationToken(this).getJWT()
        if(jwt!=""){
            intent = Intent(this,OverviewActivity::class.java)
            startActivity(intent)
            finish()
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.default_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        Action.performOperation(context,id)
        return super.onOptionsItemSelected(item)
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
