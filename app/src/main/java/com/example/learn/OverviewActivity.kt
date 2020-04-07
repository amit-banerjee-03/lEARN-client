package com.example.learn

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.learn.ApiCalls.EditUserImage
import com.example.learn.Menu.Action
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.LoadImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_overview.*
import java.io.IOException

class OverviewActivity : AppCompatActivity() {

    lateinit var context:Context
    private val GALLERY = 1
    private val CAMERA = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        setContentView(R.layout.activity_overview)
        val at=AuthenticationToken(this)
        profileName.text=at.getUserName()
        val dpUrl=at.getUserDisplayPictureUrl()
        if(!dpUrl.isNullOrEmpty()){
            LoadImage(profileImage,dpUrl).execute()
        }
        coursesButton.setOnClickListener {
            intent= Intent(this,Home::class.java)
            startActivity(intent)
        }
        virtualLabButton.setOnClickListener {
            intent= Intent(this,VirtualLabsActivity::class.java)
            startActivity(intent)
        }
        progressMonitorButton.setOnClickListener {
            intent= Intent(this,ProgressMonitorActivity::class.java)
            startActivity(intent)
        }
        articlesButton.setOnClickListener {
            intent= Intent(this,ArticlesActivity::class.java)
            startActivity(intent)
        }
        editProfilePictureButton.setOnClickListener {
            showPictureDialog(this)
        }
    }

    private fun showPictureDialog(context: Context) {
        val pictureDialog = AlertDialog.Builder(context)
        pictureDialog.setTitle("Select Image")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }


    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    EditUserImage.uploadImage(context,bitmap,profileImage)
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }

        } else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            EditUserImage.uploadImage(context,thumbnail,profileImage)
        }
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

}
