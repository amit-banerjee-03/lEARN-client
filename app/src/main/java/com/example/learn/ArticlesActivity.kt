package com.example.learn

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.ApiCalls.LoadArticle
import com.example.learn.CustomAdapters.ArticleRecycleAdapter
import com.example.learn.Menu.Action
import com.example.learn.Models.Article
import kotlinx.android.synthetic.main.activity_articles.*


class ArticlesActivity : AppCompatActivity() {

    lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        context=this
        LoadArticle.getArticles(context,articleCoverImage,articleListView,null)
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

    class LoadArticles(val context: Context, val articleListElement: RecyclerView, val articles: List<Article>): AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String? {
            return "executed"
        }

        override fun onPostExecute(result: String?) {
            articleListElement.adapter= ArticleRecycleAdapter(context,articles){ article ->
                val intent= Intent(context,LabProblemsActivity::class.java)
                intent.putExtra("ARTICLE_ID",article.id)
                context.startActivity(intent)
            }
            val layoutManager = LinearLayoutManager(context)
            articleListElement.layoutManager=layoutManager
            articleListElement.setHasFixedSize(true)
        }

    }
}
