package com.example.learn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.LoadImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_overview.*

class OverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        val at=AuthenticationToken(this)
        profileName.text=at.getUserName()
        val dpUrl=at.getUserDisplayPictureUrl()
        if(!dpUrl.isNullOrEmpty()){
            LoadImage(profileImage,dpUrl).execute()
        }
        coursesButton.setOnClickListener {
            intent= Intent(this,Home::class.java)
            startActivity(intent)
        }
        virtualLabButton.setOnClickListener {
            intent= Intent(this,VirtualLabsActivity::class.java)
            startActivity(intent)
        }
    }

}
