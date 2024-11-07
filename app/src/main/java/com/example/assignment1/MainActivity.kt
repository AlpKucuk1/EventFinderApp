package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load data from CSV or use sample data
        val events = loadEventsFromCsv()

        // Set up RecyclerView with the EventDetails adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EventDetails(events) { event ->
            openEventDetail(event)
        }
    }

    private fun openEventDetail(event: Event) {
        val intent = Intent(this, EventDetailActivity::class.java).apply {
            putExtra("eventName", event.name)
            putExtra("eventLocation", event.location)
            putExtra("eventDate", event.date)
            putExtra("eventTime", event.time)
            putExtra("eventDescription", event.description)
        }
        startActivity(intent)
    }


    private fun loadEventsFromCsv(): List<Event> {
        val events = mutableListOf<Event>()
        try {
            val inputStream = assets.open("events/MOCK_DATA.csv")
            inputStream.bufferedReader().useLines { lines ->
                lines.drop(1).forEach { line ->
                    Log.d("CSV", "Reading line: $line")  // Log each line
                    val tokens = line.split(",")
                    if (tokens.size >= 5) {
                        val event = Event(
                            id = tokens[0].toInt(),
                            name = tokens[1],
                            location = tokens[2],
                            date = tokens[3],
                            time = tokens[4],
                            description = tokens[5]
                        )
                        events.add(event)
                    }
                }
            }
            Log.d("CSV", "Total events loaded: ${events.size}")
        } catch (e: Exception) {
            Log.e("CSV", "Error reading CSV file", e)
        }
        return events
    }




}
