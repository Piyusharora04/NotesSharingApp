package com.example.notessharingapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.notessharingapp.databinding.LoginActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var emailTextLogin:String
    lateinit var passwordTextLogin:String
    lateinit var binding:LoginActivityBinding
    var check : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater, R.layout.activity_login)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize Animations

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down)

        // setting the bottom down animation on top layout

        binding.topLinearLayout.animation = bottomDown

        // creating handler for other layouts

        var handler = Handler()

        var runnable = Runnable {
            // set fade in animation for other layouts

            binding.textView1.animation = fadeIn
            binding.metaLogoCardView.animation = fadeIn
            binding.cardView.animation = fadeIn
            binding.googleCardview.animation = fadeIn
            binding.newUserSignUp.animation = fadeIn
            binding.textView3.animation = fadeIn
        }

        handler.postDelayed(runnable, 1000)

        val register = findViewById<LinearLayout>(R.id.newUserSignUp)

        val forgotPassword: TextView = findViewById(R.id.forgotPassword)
        val emailLogin: EditText = findViewById(R.id.EmailLogin)
        val passwordLogin: EditText = findViewById(R.id.PasswordLogin)
        val logInButton: Button = findViewById(R.id.LogIn)
//        val progressBar: ProgressBar = findViewById(R.id.progressBar)
//        val signUpButton:LinearLayout = findViewById(R.id.newUserSignUp)

//        var emailTextLogin = emailLogin.text.toString()
//        var passwordTextLogin = passwordLogin.text.toString()

        var auth = FirebaseAuth.getInstance()

        val user_status = intent.getStringExtra("user_status")

//        Toast.makeText(this, "$user_status", Toast.LENGTH_SHORT).show()

        if (user_status.equals("Student", ignoreCase = true)) {
            auth = FirebaseAuth.getInstance()

            if (auth.currentUser != null && auth.currentUser!!.isEmailVerified) {
                Log.d("Authentication", "Current user: ${auth.currentUser}")
                // User is signed out.
                // Redirect to the login screen or update UI.
                val Nintent = Intent(this, new_profile::class.java)
                startActivity(Nintent)
                finish()
            }
        }



        logInButton.setOnClickListener {

            auth = FirebaseAuth.getInstance()
            emailTextLogin = emailLogin.text.toString()
            passwordTextLogin = passwordLogin.text.toString()


            if (TextUtils.isEmpty(emailTextLogin) || TextUtils.isEmpty(passwordTextLogin)) {
                Toast.makeText(this, "Field can not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            progressBar.visibility = View.VISIBLE

            if (user_status == "Expert") {
                if (emailTextLogin == "arorapiyush991@gmail.com") {
                    Log.d("email", "hello")
                    // Log.d("email", "$emailTextLogin")
                    startActivity(Intent(applicationContext, ExpertActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "This id is not registered with an expert account",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            else if(user_status == "Student") {
//
                auth.signInWithEmailAndPassword(emailTextLogin, passwordTextLogin)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            val user = auth.currentUser

                            if(user != null && user.isEmailVerified) {
                                Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show()

                                // Check if the user is new or existing
                                if (task.result?.additionalUserInfo?.isNewUser == true) {
                                    // If it's a new user, start the new_profile activity
                                    val newProfileIntent = Intent(applicationContext, new_profile::class.java)
                                    startActivity(newProfileIntent)
                                } else {
                                    // If it's an existing user, start the main activity
                                    val mainIntent = Intent(applicationContext, MainActivity::class.java)
                                    startActivity(mainIntent)
                                }

                                finish()

//                                startActivity(Intent(applicationContext, new_profile::class.java))
//                                finish()
                            }
                            else{
                                Toast.makeText(this, "Please verify your email to continue.", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }

            }

        }

        forgotPassword.setOnClickListener{
            if(emailTextLogin == "") {
                Toast.makeText(this, "Please enter email address...", Toast.LENGTH_SHORT).show()
            }else{
                auth.sendPasswordResetEmail(emailTextLogin)
                Toast.makeText(this, "Password reset Mail Sent...",Toast.LENGTH_SHORT).show()
            }
        }

        register.setOnClickListener {
            val registerIntent = Intent(this, SignUpActivity::class.java)
            startActivity(registerIntent)
        }

    }
}