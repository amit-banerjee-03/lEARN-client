package com.example.learn

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.learn.Menu.Action
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.LoadImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_overview.*

class OverviewActivity : AppCompatActivity() {

    lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
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
        progressMonitorButton.setOnClickListener {
            intent= Intent(this,ProgressMonitorActivity::class.java)
            startActivity(intent)
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

}
