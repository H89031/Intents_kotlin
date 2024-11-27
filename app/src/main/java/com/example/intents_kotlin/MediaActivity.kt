package com.example.intents_kotlin

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity

class MediaActivity : AppCompatActivity(), View.OnClickListener {
    private var imgView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        findViewById<View>(R.id.btnStartCamera).setOnClickListener(this)
        findViewById<View>(R.id.btnOpenURL).setOnClickListener(this)
        findViewById<View>(R.id.btnCapturePic).setOnClickListener(this)
        findViewById<View>(R.id.btnSendText).setOnClickListener(this)

        imgView = findViewById<View>(R.id.imgCapturePic) as ImageView
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onClick(view: View) {
        val btnClick = view.id

        when (btnClick) {
            R.id.btnStartCamera -> {
                // TODO: Start the camera in photo mode
                val i = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
                try {
                    startActivity(i)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            R.id.btnCapturePic -> {
                // Take a picture and consume the returned result bitmap
                val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    startActivityForResult(i, GET_IMAGE_CAPTURE)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            R.id.btnSendText -> {
                val message = "This is a text message"

                val i = Intent(Intent.ACTION_SENDTO)

                // Use the setData function to indicate the type of data that will be sent
                // this will help the system figure out what apps to include in the chooser
                i.setData(Uri.parse("sms:18885551212"))
                i.putExtra("sms_body", message)

                try {
                    startActivity(i)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            R.id.btnOpenURL -> {
                val url = "http://www.google.com"

                // Parse the URL string using the Uri class
                val webpage = Uri.parse(url)

                val i = Intent(Intent.ACTION_VIEW, webpage)

                try {
                    startActivity(i)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
        }
    }

    // This function will be called when an activity that was started for the purpose
    // of returning a result has some data for our app to process
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Retrieve the data from the result intent and look for the bitmap
            val extras = data!!.extras
            val imageBitmap = extras!!["data"] as Bitmap?
            imgView!!.setImageBitmap(imageBitmap)
        }
    }

    companion object {
        private const val GET_IMAGE_CAPTURE = 1000
    }
}
