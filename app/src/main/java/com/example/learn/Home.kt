package com.example.learn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Models.Course
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    lateinit var adapter: ImageListView
    val videoTitles= arrayOf("Title 1","Title 2","Title 3","Title 4")
    val videoImages= arrayOf(R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background,R.drawable.learn_logo,R.drawable.learn_logo_background)
    val courses= listOf(
        Course(1,"Introduction to C","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSCNoH_-7IS7oZJWiGwPsrqJnEtN_Ws1rrQcFB-5hcNBN1JWzw5"),
        Course(2,"Up and running with Java","https://blog.eduonix.com/wp-content/uploads/2015/02/java_11.png"),
        Course(3,"HTML & CSS","https://i.udemycdn.com/course/750x422/792484_cc98_3.jpg"),
        Course(4,"Javascript essential training","https://www.tutorialrepublic.com/lib/images/javascript-illustration.png"),
        Course(5,"Programming in Php","https://www.tutorialrepublic.com/lib/images/php-illustration.png")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        adapter= ImageListView(this,courses)
        courseListView.adapter=adapter
    }
}
