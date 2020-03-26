package com.example.learn

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.ApiCalls.UserProgress
import com.example.learn.CustomAdapters.ProgressRecycleAdapter
import com.example.learn.CustomAdapters.VideoRecycleAdapter
import com.example.learn.Menu.Action
import com.example.learn.Models.*
import kotlinx.android.synthetic.main.activity_progress_monitor.*

class ProgressMonitorActivity : AppCompatActivity() {

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        setContentView(R.layout.activity_progress_monitor)
        UserProgress.getProgress(this,courseProgressRecyclerView,virtualLabProgressRecyclerView)
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

    class LoadProgress(val context: Context,val courseRecyclerView: RecyclerView,val virtualLabRecyclerView: RecyclerView,val userProgress:List<CourseProgress>, val labProgress: List<LabProgress>): AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String? {
            return "executed"
        }

        override fun onPostExecute(result: String?) {
            var userProgressElements= emptyList<Progress>().toMutableList()
            for(i in 0..userProgress.size-1){
                userProgressElements.add(Progress(userProgress[i].name,userProgress[i].percentage_completed))
            }
            var labProgressElements= emptyList<Progress>().toMutableList()
            for(i in 0..labProgress.size-1){
                labProgressElements.add(Progress(labProgress[i].name,labProgress[i].percentage_completed))
            }
            courseRecyclerView.adapter= ProgressRecycleAdapter(context,userProgressElements){ progressItem ->
            }
            virtualLabRecyclerView.adapter= ProgressRecycleAdapter(context,labProgressElements){ progressItem ->
            }
            val courseLayoutManager = LinearLayoutManager(context)
            courseRecyclerView.layoutManager=courseLayoutManager
            courseRecyclerView.setHasFixedSize(true)
            val labLayoutManager = LinearLayoutManager(context)
            virtualLabRecyclerView.layoutManager=labLayoutManager
            virtualLabRecyclerView.setHasFixedSize(true)
        }

    }
}
