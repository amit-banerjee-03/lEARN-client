package com.example.learn.CustomAdapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.learn.Models.Article
import com.example.learn.Models.Course
import com.example.learn.R
import java.lang.Exception
import java.net.URL
import java.util.*

class ArticleRecycleAdapter(
    val context: Context,
    val articles: List<Article>,
    val itemClick: (Article) -> Unit
) : Adapter<ArticleRecycleAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.article_item, parent, false)
        return Holder(view,itemClick)
    }

    override fun getItemCount(): Int {
        return articles.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bindArticle(articles[position], context)
    }

    inner class Holder(itemView: View, val itemClick: (Article) -> Unit) : ViewHolder(itemView) {
        val articleTitle = itemView?.findViewById<TextView>(R.id.articleTitle)
        val articleDescription=itemView?.findViewById<TextView>(R.id.articleDescription)

        fun bindArticle(article: Article, context: Context) {
            articleTitle.text = article.title
            articleDescription.text = article.description
            itemView.setOnClickListener{itemClick(article)}
        }
    }
}