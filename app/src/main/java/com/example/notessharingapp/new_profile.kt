package com.example.notessharingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class new_profile : AppCompatActivity() {

    private lateinit var nameText : String
    private lateinit var contactText : String
    private lateinit var fieldText : String
    private lateinit var levelText : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_profile)

        val name : EditText = findViewById(R.id.name_ET)
        val contact : EditText = findViewById(R.id.number_ET)
        val field : EditText = findViewById(R.id.field_ET)
        val continue_button : Button = findViewById(R.id.continue_but)

        val items = listOf("Beginner", "Intermediate", "Expert")

        val autoComplete : AutoCompleteTextView = findViewById(R.id.auto_complete_txt)

        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)

        autoComplete.setAdapter(adapter)


        continue_button.setOnClickListener {

            nameText = name.text.toString()
            contactText = contact.text.toString()
            fieldText = field.text.toString()


            if(TextUtils.isEmpty(nameText) || TextUtils.isEmpty(contactText) || TextUtils.isEmpty(fieldText)){
                Toast.makeText(this, "Fields can not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("keyVariableValue1", nameText)
            intent.putExtra("keyVariableValue2", contactText)
            startActivity(intent)
        }
    }
}