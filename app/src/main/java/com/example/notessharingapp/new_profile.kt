package com.example.notessharingapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.core.UserData

class new_profile : AppCompatActivity() {

    private lateinit var nameText : String
    private lateinit var contactText : String
    private lateinit var fieldText : String
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_profile)


        auth = FirebaseAuth.getInstance()

        val name : EditText = findViewById(R.id.name_ET)
        val contact : EditText = findViewById(R.id.number_ET)
        val field : EditText = findViewById(R.id.field_ET)
        val continue_button : Button = findViewById(R.id.continue_but)

        val sharedPref = getSharedPreferences("Piyush",Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("profileSet", false)) {
            redirectToMainActivity()
            return
        }

        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            redirectToMainActivity()
            return
        }

        val items = listOf("Beginner", "Intermediate", "Advanced")
        val autoComplete : AutoCompleteTextView = findViewById(R.id.auto_complete_txt)
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)
        autoComplete.setAdapter(adapter)




        Log.d("authName", "auth.currentUser : $nameText")
        continue_button.setOnClickListener {
//            val editor = sharedPref.edit()
//            editor.putString("username", nameText)
//            editor.putString("contact", contactText)
//            editor.putString("field", fieldText)
//            editor.apply()

            nameText = name.text.toString()
            contactText = contact.text.toString()
            fieldText = field.text.toString()

//            val editor = sharedPref.edit()
//            editor.putBoolean("profileSet", true)
//            editor.putString("username", nameText)
//            editor.putString("contact", contactText)
//            editor.putString("field", fieldText)
//            editor.apply()



            if(TextUtils.isEmpty(nameText) || TextUtils.isEmpty(contactText) || TextUtils.isEmpty(fieldText)){
                Toast.makeText(this, "Fields can not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveUserDataToFirebase(nameText, contactText, fieldText)

            val editor = sharedPref.edit()
            editor.putBoolean("profileSet",true)
            editor.apply()

            redirectToMainActivity()

        }
    }

    private fun saveUserDataToFirebase(name: String, contact: String, field: String) {
        // Get the current user's unique ID (email address)
        val currentUser = auth.currentUser
        val userId = currentUser?.email

        // TODO: Save user data to Firebase Realtime Database using userId
        // Example code (replace this with your database logic):
        if (userId != null) {
            val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId)
            val userData = UserProfileData(name, contact, field) // Create a data class for user data
            databaseReference.setValue(userData)
        }
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}