package com.example.intents_kotlin

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DestinationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination)

        val values = findViewById<View>(R.id.txtValues) as TextView

        // extract any data passed by the caller
        val callingIntent = intent
        if (callingIntent != null) {
            val str = callingIntent.getStringExtra("StringData")
            val int1 = callingIntent.getIntExtra("IntData", -1)

            val data = """
                $str
                $int1
                """.trimIndent()
            values.text = data
        }
    }
}
