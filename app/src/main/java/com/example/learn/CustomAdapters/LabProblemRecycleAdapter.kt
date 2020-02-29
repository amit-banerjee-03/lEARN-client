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
import com.example.learn.Models.Lab
import com.example.learn.Models.LabList
import com.example.learn.Models.LabProblem
import com.example.learn.Models.Labs
import com.example.learn.R
import java.lang.Exception
import java.net.URL
import java.util.*

class LabProblemRecycleAdapter(
    val context: Context,
    val labProblems: List<LabProblem>,
    val itemClick: (LabProblem) -> Unit
) : Adapter<LabProblemRecycleAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.lab_problem_item, parent, false)
        return Holder(view,itemClick)
    }

    override fun getItemCount(): Int {
        return labProblems.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bindLabProblem(labProblems[position], context)
    }

    inner class Holder(itemView: View, val itemClick: (LabProblem) -> Unit) : ViewHolder(itemView) {
        val caption = itemView?.findViewById<TextView>(R.id.labProblemCaption)
        val problemLevel=itemView?.findViewById<ImageView>(R.id.labProblemLevel)

        fun bindLabProblem(labProblem: LabProblem, context: Context) {
            caption.text = labProblem.caption
            if(labProblem.level=="Beginner"){
                problemLevel.setImageResource(R.mipmap.beginner_icon)
            } else if(labProblem.level=="Intermediate"){
                problemLevel.setImageResource(R.mipmap.intermediate_icon)
            } else{
                problemLevel.setImageResource(R.mipmap.advanced_icon)
            }
            itemView.setOnClickListener{itemClick(labProblem)}
        }

    }
}