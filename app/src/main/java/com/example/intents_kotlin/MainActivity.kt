package com.example.intents_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set up the UI controls
        findViewById<View>(R.id.create_explicit).setOnClickListener(this)
        findViewById<View>(R.id.create_implicit).setOnClickListener(this)
        findViewById<View>(R.id.btnMediaIntents).setOnClickListener(this)
        findViewById<View>(R.id.btnAppIntents).setOnClickListener(this)
    }

    override fun onClick(view: View) {

        // Figure out which button was clicked
        val viewClicked: Int = view.id

        if (viewClicked == R.id.create_explicit) {
            // TODO: Build an explicit Intent to launch our Activity
            val i = Intent(this, DestinationActivity::class.java)

            // TODO: send data along with the Intent to the destination
            i.putExtra("IntData", 1234)
            i.putExtra("StringData", "This is a string")

            // Start the activity with our explicit intent
            startActivity(i)
        } else if (viewClicked == R.id.create_implicit) {
            // TODO: Build an implicit intent to handle a type of action
            val textMessage = "This is a sample message"
            val i = Intent()
            i.setAction(Intent.ACTION_SEND)
            i.setType("text/plain")
            i.putExtra(Intent.EXTRA_TEXT, textMessage)

            // TODO: use an intent chooser to force a choose dialog
            val chooser = Intent.createChooser(i, "Select an app:")

            // Verify that the intent will resolve to an activity
            if (i.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            }
            // Typically you would handle the null case here by informing the user
            // that there is no installed app to handle this intent or
            // by taking some other action
        }


        // Handle button clicks to start the other intent examples
        if (viewClicked == R.id.btnAppIntents) {
            val i = Intent(this, AppsActivity::class.java)
            startActivity(i)
        } else if (viewClicked == R.id.btnMediaIntents) {
            val i = Intent(this, MediaActivity::class.java)
            startActivity(i)
        }
    }
}