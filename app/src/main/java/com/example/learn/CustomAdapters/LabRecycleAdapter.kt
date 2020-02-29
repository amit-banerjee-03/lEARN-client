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
import com.example.learn.Models.Labs
import com.example.learn.R
import java.lang.Exception
import java.net.URL
import java.util.*

class LabRecycleAdapter(
    val context: Context,
    val labs: List<LabList>,
    val itemClick: (LabList) -> Unit
) : Adapter<LabRecycleAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.virtual_lab_item, parent, false)
        return Holder(view,itemClick)
    }

    override fun getItemCount(): Int {
        return labs.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bindLab(labs[position], context)
    }

    class LoadImage(val labImage: ImageView?, val image: String) : AsyncTask<String, Void, Bitmap>() {
        override fun doInBackground(vararg p0: String?): Bitmap? {
            var bmp: Bitmap = Bitmap.createBitmap(100, 50, Bitmap.Config.ARGB_8888)
            try {
                val url = URL(image)
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: Exception) {
                Log.i("Image Not Loaded", e.toString())
            }
            return bmp
        }

        override fun onPostExecute(result: Bitmap?) {
            labImage?.setImageBitmap(result)
        }

    }

    inner class Holder(itemView: View, val itemClick: (LabList) -> Unit) : ViewHolder(itemView) {
        val labImage = itemView?.findViewById<ImageView>(R.id.labImage)
        val labName = itemView?.findViewById<TextView>(R.id.labName)
        val labLevel=itemView?.findViewById<ImageView>(R.id.labLevel)

        fun bindLab(lab: LabList, context: Context) {
            labName.text = lab.name
            if(lab.level=="Beginner"){
                labLevel.setImageResource(R.mipmap.beginner_icon)
            } else if(lab.level=="Intermediate"){
                labLevel.setImageResource(R.mipmap.intermediate_icon)
            } else{
                labLevel.setImageResource(R.mipmap.advanced_icon)
            }
            LoadImage(labImage, lab.cover_image).execute()
            itemView.setOnClickListener{itemClick(lab)}
        }

    }
}