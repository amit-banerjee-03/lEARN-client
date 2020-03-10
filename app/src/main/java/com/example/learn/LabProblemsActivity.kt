package com.example.learn

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.ApiCalls.LabDetails
import com.example.learn.CustomAdapters.LabProblemRecycleAdapter
import com.example.learn.CustomAdapters.LabRecycleAdapter
import com.example.learn.Models.Lab
import com.example.learn.Models.LabProblem
import kotlinx.android.synthetic.main.activity_lab_problems.*

class LabProblemsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_problems)
        LabDetails.getLab(this,labProblemsListView,intent.getIntExtra("LAB_ID",0))
    }

    class LoadLabProblems(val context: Context, val labProblemElement: RecyclerView, val labProblems: List<LabProblem>): AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String? {
            return "executed"
        }

        override fun onPostExecute(result: String?) {
            labProblemElement.adapter= LabProblemRecycleAdapter(context,labProblems){ labProblem ->
                val intent=Intent(context,LabProblemActivity::class.java)
                intent.putExtra("ID",labProblem.id)
                intent.putExtra("CAPTION",labProblem.caption)
                intent.putExtra("DESCRIPTION",labProblem.problem_description)
                context.startActivity(intent)
            }
            val layoutManager = LinearLayoutManager(context)
            labProblemElement.layoutManager=layoutManager
            labProblemElement.setHasFixedSize(true)
        }

    }
}
