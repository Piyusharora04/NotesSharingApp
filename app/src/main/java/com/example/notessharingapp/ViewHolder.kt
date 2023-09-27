package com.example.notessharingapp

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val item_combined: ConstraintLayout = itemView.findViewById(R.id.item_combined)
}
