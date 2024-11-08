package com.example.assignment1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1.databinding.ItemEventBinding

class EventDetails(
    private val events: List<Event>,
    private val onItemClick: (Event) -> Unit
) : RecyclerView.Adapter<EventDetails.EventViewHolder>() {

    // ViewHolder class for each event item
    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {

        // Bind the event to the UI and set the click listener
        fun bind(event: Event) {
            binding.event = event
            binding.root.setOnClickListener { onItemClick(event) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding: ItemEventBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_event,
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position]) // Bind each event to the holder
    }

    override fun getItemCount() = events.size // Return the number of events
}
