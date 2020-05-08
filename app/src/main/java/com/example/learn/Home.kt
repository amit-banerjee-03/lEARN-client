package com.example.learn

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.ApiCalls.HomePageCourses
import com.example.learn.CustomAdapters.CourseRecycleAdapter
import com.example.learn.Menu.Action
import com.example.learn.Models.Course
import kotlinx.android.synthetic.main.activity_home.*


class Home : AppCompatActivity() {

    lateinit var adapter: CourseRecycleAdapter
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        context=this
        courseListView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        HomePageCourses.getCourses(this,courseListView,null)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home,menu)
        val searchItem=menu.findItem(R.id.search)
        if(searchItem!=null){
            val searchView=searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    HomePageCourses.getCourses(context,courseListView,newText)
                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        Action.performOperation(context,id)
        return super.onOptionsItemSelected(item)
    }

    class LoadCourses(context: Context,courseListElement:RecyclerView,courses:List<Course>): AsyncTask<String, Void, String>(){
        val courses=courses
        val context=context
        val courseListElement=courseListElement
        override fun doInBackground(vararg p0: String?): String? {
            return "executed"
        }

        override fun onPostExecute(result: String?) {
            courseListElement.adapter=CourseRecycleAdapter(context,courses){course ->
                val intent = Intent(context,VideoList::class.java)
                intent.putExtra("COURSE_ID",course.id)
                context.startActivity(intent)
            }
            val layoutManager = LinearLayoutManager(context)
            courseListElement.layoutManager=layoutManager
            courseListElement.setHasFixedSize(true)
        }

    }


}
