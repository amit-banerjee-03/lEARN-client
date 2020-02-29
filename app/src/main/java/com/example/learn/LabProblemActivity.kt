package com.example.learn

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import kotlinx.android.synthetic.main.activity_lab_problem.*
import kotlinx.android.synthetic.main.lab_problem_item.*

class LabProblemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_problem)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            problemCaption.text=Html.fromHtml(intent.getStringExtra("CAPTION"), Html.FROM_HTML_MODE_COMPACT)
            problemDescription.text=Html.fromHtml(intent.getStringExtra("DESCRIPTION"), Html.FROM_HTML_MODE_COMPACT)
        } else {
            problemCaption.text=Html.fromHtml(intent.getStringExtra("CAPTION"))
            problemDescription.text=Html.fromHtml(intent.getStringExtra("CAPTION"))
        }
    }
}
