package com.example.learn

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.learn.ApiCalls.CompileCode
import com.example.learn.ApiCalls.RunCode
import com.example.learn.ApiCalls.SubmitCode
import com.example.learn.Utils.Programming
import com.example.learn.Utils.Utility
import kotlinx.android.synthetic.main.activity_code_editor.*

class CodeEditorActivity : AppCompatActivity(),CustomInputDialog.CustomInputDialogListener {

    private var customInputString:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_editor)
        var languages=Programming.getSupportedLanguages()
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,languages)
        selectLanguage.adapter=adapter
        compileButton.setOnClickListener {
            outputId.text="Compiling..."
            val selectedLanguage=selectLanguage.selectedItem.toString()
            CompileCode.compile(this,codeEditor.text.toString(),Programming.getLanguageCode(selectedLanguage)?:"",outputId,customInputString)
        }
        runButton.setOnClickListener {
            outputId.text="Executing..."
            val selectedLanguage=selectLanguage.selectedItem.toString()
            RunCode.run(this,codeEditor.text.toString(),Programming.getLanguageCode(selectedLanguage)?:"",outputId,customInputString)
        }

        customInputButton.setOnClickListener {
            openCustomInputDialog()
        }

        submitButton.setOnClickListener {
            outputId.text="Request enqueued...Please wait..."
            val selectedLanguage=selectLanguage.selectedItem.toString()
            SubmitCode.submit(this,codeEditor.text.toString(),Programming.getLanguageCode(selectedLanguage)?:"",outputId,intent.getIntExtra("ID",0))
        }

    }

    public fun openCustomInputDialog() {
        val customInputDialog=CustomInputDialog(this)
        customInputDialog.show(supportFragmentManager,"custom input dialog")
    }

    override fun applyText(input: String) {
        customInputString=input
    }
}
