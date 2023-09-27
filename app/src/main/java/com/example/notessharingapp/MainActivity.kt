package com.example.notessharingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newNote : FloatingActionButton = findViewById(R.id.newNote)

        newNote.setOnClickListener{
            val intent = Intent(this, upload_notes::class.java)
            startActivity(intent)
        }
    }
}