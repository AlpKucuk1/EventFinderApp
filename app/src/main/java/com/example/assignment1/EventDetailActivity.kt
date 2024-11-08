package com.example.assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.assignment1.databinding.ActivityEventDetailBinding

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_detail)

        // Go back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Retrieve event details from the intent and create an Event object
        val event = Event(
            id = intent.getIntExtra("eventId", 0),
            name = intent.getStringExtra("eventName") ?: "",
            location = intent.getStringExtra("eventLocation") ?: "",
            date = intent.getStringExtra("eventDate") ?: "",
            time = intent.getStringExtra("eventTime") ?: "",
            description = intent.getStringExtra("eventDescription") ?: ""
        )

        // Bind the event data to the layout
        binding.event = event
    }

    // Handle the back button click
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
