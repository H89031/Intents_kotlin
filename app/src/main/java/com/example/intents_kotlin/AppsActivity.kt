package com.example.intents_kotlin

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class AppsActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps)

        findViewById<View>(R.id.btnSetAlarm).setOnClickListener(this)
        findViewById<View>(R.id.btnShowMapLoc).setOnClickListener(this)
        findViewById<View>(R.id.btnStartPhoneCall).setOnClickListener(this)
        findViewById<View>(R.id.btnSendAnEmail).setOnClickListener(this)
        findViewById<View>(R.id.btnPendIntent).setOnClickListener(this)

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("my_channel", "Android Intents", importance)
        channel.description = "Test Notification Channel"
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
    }


    @SuppressLint("QueryPermissionsNeeded")
    override fun onClick(v: View) {
        val btnClick = v.id

        when (btnClick) {
            R.id.btnSetAlarm -> {
                val message = "Time to wake up!"
                val hour = 6
                val minutes = 30

                // Create an intent to tell the system to set an alarm
                // NOTE: your app needs to have the Set Alarm permission
                val i = Intent(AlarmClock.ACTION_SET_ALARM)
                    .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                    .putExtra(AlarmClock.EXTRA_HOUR, hour)
                    .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                    .putExtra(AlarmClock.EXTRA_VIBRATE, true)

                try {
                    startActivity(i)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            R.id.btnShowMapLoc -> {
                // Locations can be specified using latlongs, queries, addresses, etc.
                val location = "geo:37.4220,-122.0841"

                //            String location = "geo:0,0?q=37.4220,-122.0841(GooglePlex)";
    //            String location = "geo:0,0?q=20+W+34th+St+10001";
    //            String location = "geo:47.6205,-122.3493?q=restaurants";

                // Parse the location using the Uri class
                val geoLocUri = Uri.parse(location)

                // Pass the Uri directly to the Intent constructor
                val i = Intent(Intent.ACTION_VIEW, geoLocUri)

                try {
                    startActivity(i)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            R.id.btnSendAnEmail -> {
                val addresses = arrayOf("test@example.com")
                val ccs = arrayOf("someone@example.com")
                val subject = "This is a test"
                val message = "This is a test email message!"

                val i = Intent(Intent.ACTION_SENDTO)

                // Use setData to ensure that only email apps respond
                i.setData(Uri.parse("mailto:"))

                i.putExtra(Intent.EXTRA_EMAIL, addresses)
                i.putExtra(Intent.EXTRA_SUBJECT, subject)
                i.putExtra(Intent.EXTRA_CC, ccs)
                i.putExtra(Intent.EXTRA_TEXT, message)

                try {
                    startActivity(i)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            R.id.btnStartPhoneCall -> {
                val phoneNumber = "1-800-555-1212"

                // Build the Uri for the phone number
                val numUri = Uri.parse("tel:$phoneNumber")

                // Your application needs the CALL_PHONE permission for this intent
                val i = Intent(Intent.ACTION_DIAL)
                //            Intent i = new Intent(Intent.ACTION_CALL); // Actually makes the call
                // Set the Uri as the intent data
                i.setData(numUri)

                try {
                    startActivity(i)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            R.id.btnWebSearch -> {
                val queryStr = "Eiffel Tower"

                // Create an intent to fire off a web search
                val i = Intent(Intent.ACTION_WEB_SEARCH)

                i.putExtra(SearchManager.QUERY, queryStr)
                try {
                    startActivity(i)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            R.id.btnPendIntent -> {
                val builder = NotificationCompat.Builder(this, "my_channel")

                // Create the intent that will be fired when the user taps the notification
                val intent = Intent(
                    this,
                    DestinationActivity::class.java
                )
                // Wrap it up in a PendingIntent
                val pendingIntent = PendingIntent.getActivity(
                    this,
                    NOTIFY_ID,
                    intent,
                    PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                builder.setContentTitle("Sample Notification")
                builder.setContentText("This is a sample notification")
                builder.setAutoCancel(true)
                builder.setSubText("Tap to view")
                builder.setContentIntent(pendingIntent)

                val notification = builder.build()
                val mgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                mgr.notify(NOTIFY_ID, notification)
            }
        }
    }

    companion object {
        private const val NOTIFY_ID = 1001
    }
}

