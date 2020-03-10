package com.example.learn.ApiCalls

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.text.Html
import android.util.Base64
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.*
import com.example.learn.Api.*
import com.example.learn.Config.Constants
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Models.*
import com.example.learn.Utils.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_lab_problem.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object SubmitCode {
    fun submit(context: Context,source:String,lang:String,outputText:TextView,labProblemId:Int) {
        RetrofitClientCode.instance.runTestCases("Bearer ${AuthenticationToken(context).getJWT()}",Utility.escapeString(source),lang,labProblemId)
            .enqueue(object: Callback<Submit> {
                override fun onFailure(call: Call<Submit>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }
                override fun onResponse(call: Call<Submit>, response: Response<Submit>) {
                    try{
                        if(response.isSuccessful) {
                            var outputHtml="<font color='#00574B'>Test Cases</font><br/>"
                            var testCases=response.body()!!.test_cases
                            if(testCases.isEmpty()){
                                outputHtml="<font color='#FF0000'>No Test Cases Found For This Problem</font><br/>"
                            } else{
                                for(testCase in testCases){
                                    outputHtml+="<font color='#000000'><b>Test Case ${testCase.id}</b></font>: "
                                    if(testCase.passed){
                                        outputHtml+="<font color='#00574B'><b>Passed</b></font>"
                                    } else{
                                        outputHtml+="<font color='#FF0000'><b>Failed</b></font>"
                                    }
                                    outputHtml+="<br/>";
                                }
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                outputText.text= Html.fromHtml(outputHtml, Html.FROM_HTML_MODE_COMPACT)
                            } else {
                                outputText.text= Html.fromHtml(outputHtml)
                            }
                        } else{
                            val error= ApiError().getError(response.errorBody()?.string())
                            outputText.text=error
                            if(response.code()==401){
                                AuthenticationToken(context).setJWT("")
                                StartLoginActivity().execute(context)
                            }
                        }
                    }catch (e:Exception){
                        Log.v("Coding",e.toString())
                        outputText.text="Error submitting code!!"
                        return
                    }
                }
            })
    }
}