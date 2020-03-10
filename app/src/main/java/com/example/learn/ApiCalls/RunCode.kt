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

object RunCode {
    fun run(context: Context,source:String,lang:String,outputText:TextView,customInputString: String) {
        RetrofitClientCode.instance.run("Bearer ${AuthenticationToken(context).getJWT()}",Utility.escapeString(source),lang,customInputString)
            .enqueue(object: Callback<CodeRun> {
                override fun onFailure(call: Call<CodeRun>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }
                override fun onResponse(call: Call<CodeRun>, response: Response<CodeRun>) {
                    try{
                        if(response.isSuccessful) {
                            outputText.text="C${response.body()!!.response.compile_status}"
                            var outputHtml=""
                            if(response.body()!!.response.run_status.exit_code!="0"){
                                outputHtml="<font color='#ff0000'>Exit Code ${response.body()!!.response.run_status.exit_code}</font><br/>"
                                outputHtml+=response.body()!!.response.run_status.status+"\n"+response.body()!!.response.run_status.status_detail
                            } else{
                                outputHtml="<font color='#00574B'>Executed Successfully</font><br/>"
                                outputHtml+=response.body()!!.response.run_status.output_html
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
                        outputText.text="Error running code!!"
                        return
                    }
                }
            })
    }
}