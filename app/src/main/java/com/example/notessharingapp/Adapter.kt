package com.example.notessharingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val data: List<DataItem>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val note_item: LinearLayout? = itemView.findViewById<LinearLayout>(R.id.notes_item)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_main, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        val item_view = holder.note_item
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
