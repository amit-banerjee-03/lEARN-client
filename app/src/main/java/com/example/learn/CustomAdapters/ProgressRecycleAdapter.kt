package com.example.learn.CustomAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.learn.Models.Progress
import com.example.learn.R

class ProgressRecycleAdapter(
    val context: Context,
    val progressItems: List<Progress>,
    val itemClick: (Progress) -> Unit
) : Adapter<ProgressRecycleAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.progress_monitor_item, parent, false)
        return Holder(view,itemClick)
    }

    override fun getItemCount(): Int {
        return progressItems.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bindProgressItem(progressItems[position], context)
    }

    inner class Holder(itemView: View, val itemClick: (Progress) -> Unit) : ViewHolder(itemView) {
        val title = itemView?.findViewById<TextView>(R.id.progressTitle)
        val progressBar=itemView?.findViewById<ProgressBar>(R.id.progressPercentage)
        val progressPercentage = itemView?.findViewById<TextView>(R.id.progressPercentageValue)
        fun bindProgressItem(progressItem: Progress, context: Context) {
            title.text = progressItem.name
            progressBar.progress=progressItem.progress.toInt()
            progressPercentage.text="${progressItem.progress.toInt()} %"
            itemView.setOnClickListener{itemClick(progressItem)}
        }

    }

}