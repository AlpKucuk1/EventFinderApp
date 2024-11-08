package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.PopupMenu
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var events: List<Event>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load data from CSV
        events = loadEventsFromCsv()

        // Set up RecyclerView with the EventDetails adapter
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EventDetails(events) { event ->
            openEventDetail(event)
        }

        val filterButton = findViewById<Button>(R.id.sortButton)
        filterButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.filter_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.filter_name -> {
                        filterEvents("Name")
                        true
                    }
                    R.id.filter_city -> {
                        filterEvents("City")
                        true
                    }
                    R.id.filter_date -> {
                        filterEvents("Date")
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
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

    private fun filterEvents(criteria: String) {
        val filteredEvents = when (criteria) {
            "Name" -> events.sortedBy { it.name }
            "City" -> events.sortedBy { it.location }
            "Date" -> events.sortedBy { it.date }
            else -> events
        }
        recyclerView.adapter = EventDetails(filteredEvents) { event ->
            openEventDetail(event)
        }
    }

    private fun loadEventsFromCsv(): List<Event> {
        val events = mutableListOf<Event>()
        try {
            val inputStream = assets.open("events/MOCK_DATA.csv")
            inputStream.bufferedReader().useLines { lines ->
                lines.drop(1).forEach { line ->
                    Log.d("CSV", "Reading line: $line")
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
