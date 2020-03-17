package com.example.learn

import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.example.learn.Utils.AuthenticationToken
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        ProgressBarProgress(this).execute()
    }

    class ProgressBarProgress(var obj:SplashScreenActivity): AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg p0: Unit?) {
            for(i in 1 .. 51){
                obj.setProgressBarProgress(i*2)
                Thread.sleep(10)
            }
        }
        override fun onPostExecute(result: Unit?) {
            obj.loadHomeActivity()
        }
    }

    private fun setProgressBarProgress(percent:Int){
        startupProgressBar.progress=percent
        var color:Int
        if(percent<40){
            color=Color.RED
        } else if(percent<70){
            color=Color.YELLOW
        } else{
            color=Color.GREEN
        }
        if(percent==40 || percent==70) {
            startupProgressBar.progressTintList = ColorStateList.valueOf(color)
        }
    }

    private fun loadHomeActivity(){
        val jwt= AuthenticationToken(this).getJWT()
        var activityIntent:Intent
        if(jwt!=""){
            activityIntent = Intent(this,OverviewActivity::class.java)
        } else{
            activityIntent = Intent(this,MainActivity::class.java)
        }
        startActivity(activityIntent)
        finish()
    }
}
