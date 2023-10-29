package com.example.notessharingapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var data:List<NoteData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        return ViewHolder(itemView)
    }

    fun setData(data: List<NoteData>) {
        this.data=data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.amount.text=currentItem.amount
        holder.noteName.text=currentItem.name
        Glide.with(holder.itemView.context)
            .load(currentItem.img)
            .into(holder.displayImage)

        holder.itemView.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        Log.i("debug",data.size.toString())
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val displayImage = itemView.findViewById<ImageView>(R.id.display_image)
        val amount= itemView.findViewById<TextView>(R.id.amount)
        val noteName = itemView.findViewById<TextView>(R.id.note_name)

    }

}
