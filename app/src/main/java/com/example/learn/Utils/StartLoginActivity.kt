package com.example.learn.Utils

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import com.example.learn.Home
import com.example.learn.MainActivity

class StartLoginActivity: AsyncTask<Context, Context, Context>(){
    override fun doInBackground(vararg p0: Context?): Context? {
        return p0[0]
    }

    override fun onPostExecute(result: Context?) {
        result!!.startActivity(Intent(result, MainActivity::class.java))
        (result as Home).finish()
    }

}