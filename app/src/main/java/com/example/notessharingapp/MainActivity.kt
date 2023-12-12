package com.example.notessharingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notessharingapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
//    private var isBlackTint = false
//    private lateinit var fav: ImageView
    private lateinit var noteAdapter : NoteAdapter
    private lateinit var adapter : NoteAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView
//    private lateinit var tempList : List<NoteData>

//    private lateinit var verified_check : String
//    private lateinit var mainList : ArrayList<NoteData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteAdapter= NoteAdapter()

//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewHome)
        binding.recyclerViewHome.apply {
            adapter=noteAdapter
            layoutManager=LinearLayoutManager(this@MainActivity)
        }

        val newNote : FloatingActionButton = findViewById(R.id.newNote)

        newNote.setOnClickListener{
            val intent = Intent(this, upload_notes::class.java)
            startActivity(intent)
        }
        auth = FirebaseAuth.getInstance()
        val profile_name = findViewById<TextView>(R.id.profile_name)
        val profile_contact = findViewById<TextView>(R.id.profile_number)
//        val profile_field = findViewById<EditText>(R.id.field_ET)

        val logOut : TextView = findViewById(R.id.log_out)

        logOut.setOnClickListener{
            auth.signOut()
            val logOutIntent = Intent(this, LoginActivity::class.java)
            startActivity(logOutIntent)
            finish()
        }

        val quit = findViewById<TextView>(R.id.quit)

        quit.setOnClickListener {
            finish()
        }


        val name_profile = intent.getStringExtra("user_name").toString()
        val contact_profile = intent.getStringExtra("user_contact").toString()
        val field_profile = intent.getStringExtra("user_filed").toString()
//        val field_profile = intent.getStringExtra("keyVariableValue3").toString()

        // for setting the profile info on the navigation drawer in main activity.
        profile_name.text = name_profile
        profile_contact.text = contact_profile

        retrieveDataFromFirebase()

        val profile : ImageView = findViewById(R.id.profile_icon)

        profile.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            intent.putExtra("profile_name", name_profile)
            intent.putExtra("profile_contact", contact_profile)
            intent.putExtra("profile_field", field_profile)
//            Log.d("profile",field_profile.toString())
            startActivity(intent)
        }

        val upiID = intent.getStringExtra("userUPI").toString()
        val amount = intent.getStringExtra("userAmount").toString()
        val description = intent.getStringExtra("userDes").toString()

        val intent = Intent(this,PurchasePreview::class.java)
        intent.putExtra("dex", description)




    }

    // Function to retrieve data from Firebase
    private fun retrieveDataFromFirebase() {
        FirebaseApp.initializeApp(this)

        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.reference.child("New Data")


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("debug_check", " haha ")
                if (dataSnapshot.exists()) {
                    val dataList = dataSnapshot.children
                    val listData = mutableListOf<NoteData>()
                    for( ds in dataList){
                        Log.i("debug",ds.value.toString())
                        val j = ds.value as HashMap<*, *>
                        val k=NoteData(
                            j.get("name").toString(),
                            j.get("pdf").toString(),
                            j.get("img").toString(),
                            j.get("upiID").toString(),
                            j.get("description").toString(),
                            j.get("amount").toString(),
                            j.get("purchased").toString(),
                            j.get("verified").toString()
                        )
                        listData.add(k)
                    }

                    noteAdapter.setData(listData)

//                    val myDataFromDatabase = dataSnapshot.getValue(MainHomeView::class.java)
//                    if (myDataFromDatabase != null) {
//                        // Handle the retrieved data
//                        display_amount.text =
//
//                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                println("Failed to read value from Firebase: ${databaseError.toException()}")
            }
        })

//        Log.d("test_upload", )
    }
}