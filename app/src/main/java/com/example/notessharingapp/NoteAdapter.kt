package com.example.notessharingapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var datas:List<NoteData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        return ViewHolder(itemView)
    }

    fun setData(data: MutableList<NoteData>) {
        this.datas = data
        notifyDataSetChanged()
    }

//    fun filterList(filterlist: List<NoteData>) {
//        // below line is to add our filtered
//        // list in our course array list.
//        datas = filterlist
//        // below line is to notify our adapter
//        // as change in recycler view data.
//        notifyDataSetChanged()
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = datas[position]
        holder.amount.text=currentItem.amount
        holder.noteName.text=currentItem.name
        Glide.with(holder.itemView.context)
            .load(currentItem.img)
            .into(holder.displayImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, PurchasePreview::class.java)

            intent.putExtra("uploadedBy",currentItem.name)
            intent.putExtra("upiID",currentItem.upiID)
            intent.putExtra("price",currentItem.amount)
            intent.putExtra("pdf",currentItem.pdf)
            intent.putExtra("image",currentItem.img)
            intent.putExtra("dex",currentItem.description)
            intent.putExtra("purchase",currentItem.purchased)

//            Log.d("adapter_check", currentItem.pdf.toString())
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        Log.i("debug",datas.size.toString())
        return datas.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val displayImage = itemView.findViewById<ImageView>(R.id.display_image)
        val amount= itemView.findViewById<TextView>(R.id.amount)
        val noteName = itemView.findViewById<TextView>(R.id.note_name)
        

    }




}
