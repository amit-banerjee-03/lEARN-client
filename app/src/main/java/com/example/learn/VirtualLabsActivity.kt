package com.example.learn

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.ApiCalls.LabList
import com.example.learn.CustomAdapters.CourseRecycleAdapter
import com.example.learn.CustomAdapters.LabProblemRecycleAdapter
import com.example.learn.CustomAdapters.LabRecycleAdapter
import com.example.learn.Menu.Action
import com.example.learn.Models.Course
import com.example.learn.Models.Labs
import kotlinx.android.synthetic.main.activity_virtual_labs.*

class VirtualLabsActivity : AppCompatActivity() {

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        setContentView(R.layout.activity_virtual_labs)
        LabList.getLabs(this,labListView)
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

    class LoadLabs(val context: Context, val labListElement: RecyclerView, val labs: List<com.example.learn.Models.LabList>): AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String? {
            return "executed"
        }

        override fun onPostExecute(result: String?) {
            labListElement.adapter= LabRecycleAdapter(context,labs){ lab ->
                val intent=Intent(context,LabProblemsActivity::class.java)
                intent.putExtra("LAB_ID",lab.id)
                context.startActivity(intent)
            }
            val layoutManager = LinearLayoutManager(context)
            labListElement.layoutManager=layoutManager
            labListElement.setHasFixedSize(true)
        }

    }
}
