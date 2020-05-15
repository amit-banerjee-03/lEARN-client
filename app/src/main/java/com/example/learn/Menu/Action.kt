package com.example.learn.Menu

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.learn.R
import com.example.learn.SettingsActivity
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.StartLoginActivity

object Action {
    private fun click(context:Context,buttonTag:String){
        when(buttonTag){
            "SETTINGS"->{
                val intent=Intent(context,SettingsActivity::class.java)
                context.startActivity(intent)
            }
            "ABOUT_US"->Toast.makeText(context,"Open about us",Toast.LENGTH_LONG).show()
            "LOG_OUT"->{
                AuthenticationToken(context).setJWT("")
                StartLoginActivity().execute(context)
            } else->Toast.makeText(context,"Unknown option selected",Toast.LENGTH_LONG).show()
        }
    }
    fun performOperation(context: Context,id:Int){
        when(id){
            R.id.settingsButton,R.id.settingsButton_h->click(context,"SETTINGS")
            R.id.aboutButton,R.id.aboutButton_h->click(context,"ABOUT_US")
            R.id.logOutButton,R.id.logOutButton_h->click(context,"LOG_OUT")
        }
    }
}