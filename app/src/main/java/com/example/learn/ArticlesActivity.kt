package com.example.learn

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.widget.SearchView
import com.example.learn.ApiCalls.HomePageCourses
import com.example.learn.Config.Constants
import com.example.learn.Menu.Action
import kotlinx.android.synthetic.main.activity_articles.*
import kotlinx.android.synthetic.main.activity_home.*

class ArticlesActivity : AppCompatActivity() {

    lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        context=this
        articlesWebView.settings.javaScriptEnabled=true
        articlesWebView.settings.domStorageEnabled=true
        articlesWebView.settings.builtInZoomControls=true
        articlesWebView.settings.javaScriptCanOpenWindowsAutomatically=true
        articlesWebView.settings.setGeolocationEnabled(true)
        articlesWebView.settings.setGeolocationDatabasePath(filesDir.path)
        articlesWebView.webViewClient= WebViewClient()
        articlesWebView.loadUrl("https://www.w3schools.com/")
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
