package com.example.notessharingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
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

        val profile_name = findViewById<TextView>(R.id.profile_name)
        val profile_contact = findViewById<TextView>(R.id.profile_number)

        val name_profile = intent.getStringExtra("keyVariableValue1")
        val contact_profile = intent.getStringExtra("keyVariableValue2")

        profile_name.text = name_profile
        profile_contact.text = contact_profile
    }
}