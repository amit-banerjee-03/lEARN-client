package com.example.learn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.LoadImage
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    lateinit var authenticationToken:AuthenticationToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        authenticationToken=AuthenticationToken(this)
        val displayImageUrl=authenticationToken.getUserDisplayPictureUrl()
        LoadImage(profileImage,displayImageUrl)
        profileName.text=authenticationToken.getUserName()
    }
}
