package com.example.learn

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.ApiCalls.CourseCompletionCertificate
import com.example.learn.ApiCalls.UserProgress
import com.example.learn.CustomAdapters.ProgressRecycleAdapter
import com.example.learn.CustomAdapters.VideoRecycleAdapter
import com.example.learn.Menu.Action
import com.example.learn.Models.*
import kotlinx.android.synthetic.main.activity_progress_monitor.*
import kotlin.math.roundToInt

class ProgressMonitorActivity : AppCompatActivity() {

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        setContentView(R.layout.activity_progress_monitor)
        UserProgress.getProgress(this,courseProgressRecyclerView,virtualLabProgressRecyclerView)
        setWriteStoragePermission()
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

    fun setWriteStoragePermission(){
        val permission= ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(permission!= PackageManager.PERMISSION_GRANTED){
            Log.v("Coding","Permission Denied")
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            val builder=androidx.appcompat.app.AlertDialog.Builder(this)
            builder.setMessage("Permission to write in external storage is required")
            builder.setTitle("Permission Required!")
            builder.setPositiveButton("Ok")
            {
                    dialog,which->
                Log.v("Coding","Clicked")
                makeRequest()
            }
            val dialog=builder.create()
            dialog.show()
        } else{
            makeRequest()
        }
    }

    fun makeRequest(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1->{
                if(grantResults.isEmpty() || grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Log.v("Coding","Permission has been denied by user")
                } else{
                    Log.v("Coding","Permission has been granted by user")
                }
            }
        }
    }

    class LoadProgress(val context: Context,val courseRecyclerView: RecyclerView,val virtualLabRecyclerView: RecyclerView,val userProgress:List<CourseProgress>, val labProgress: List<LabProgress>): AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String? {
            return "executed"
        }

        override fun onPostExecute(result: String?) {
            var userProgressElements= emptyList<Progress>().toMutableList()
            for(i in 0..userProgress.size-1){
                userProgressElements.add(Progress(userProgress[i].id,userProgress[i].name,userProgress[i].percentage_completed))
            }
            var labProgressElements= emptyList<Progress>().toMutableList()
            for(i in 0..labProgress.size-1){
                labProgressElements.add(Progress(labProgress[i].id,labProgress[i].name,labProgress[i].percentage_completed))
            }
            courseRecyclerView.adapter= ProgressRecycleAdapter(context,userProgressElements){ progressItem ->
                if(progressItem.progress.toInt()==100) {
                    CourseCompletionCertificate.getCertificate(context, progressItem.id)
                } else{
                    Toast.makeText(context,"Complete the remaining "+(100-progressItem.progress).roundToInt()+"% to download the certificate of completion",Toast.LENGTH_LONG).show()
                }
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
