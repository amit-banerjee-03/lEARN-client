package com.example.learn

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.ApiCalls.LoadArticle
import com.example.learn.CustomAdapters.ArticleRecycleAdapter
import com.example.learn.Menu.Action
import com.example.learn.Models.Article
import kotlinx.android.synthetic.main.activity_article_details.*

class ArticleDetailsActivity : AppCompatActivity() {

    lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)
        context=this
        val id=intent.getIntExtra("ARTICLE_ID",0)
        com.example.learn.ApiCalls.LoadArticle.getArticle(context,articleBody,id)
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

    class LoadArticle(val context: Context, val articleDetails: TextView, val article: Article): AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String? {
            return "executed"
        }

        override fun onPostExecute(result: String?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                articleDetails.text= Html.fromHtml(article.body, Html.FROM_HTML_MODE_COMPACT)
            } else {
                articleDetails.text= Html.fromHtml(article.body)
            }
        }

    }
}
