package com.example.assignment1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        // Retrieve event details from the intent
        val name = intent.getStringExtra("eventName")
        val location = intent.getStringExtra("eventLocation")
        val date = intent.getStringExtra("eventDate")
        val time = intent.getStringExtra("eventTime")
        val description = intent.getStringExtra("eventDescription")

        // Set the retrieved data to TextViews
        findViewById<TextView>(R.id.eventNameView).text = name
        findViewById<TextView>(R.id.eventLocationView).text = location
        findViewById<TextView>(R.id.eventDateView).text = "$date at $time"
        findViewById<TextView>(R.id.eventDescriptionView).text = description
    }
}
