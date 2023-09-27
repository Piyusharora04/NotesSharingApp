package com.example.notessharingapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.animation.AnimationUtils
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
    lateinit var binding:LoginActivityBinding
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
        val logInButton: AppCompatButton = findViewById(R.id.LogIn)
//        val progressBar: ProgressBar = findViewById(R.id.progressBar)
//        val signUpButton:LinearLayout = findViewById(R.id.newUserSignUp)
//
//        var emailTextLogin = emailLogin.text.toString()
//        var passwordTextLogin = passwordLogin.text.toString()
//
//        var auth = FirebaseAuth.getInstance()
//
//        val intent = Intent(this, new_profile::class.java)
//
//        logInButton.setOnClickListener {
//            auth = FirebaseAuth.getInstance()
//            emailTextLogin = emailLogin.text.toString()
//            passwordTextLogin = passwordLogin.text.toString()
//
//            if (TextUtils.isEmpty(emailTextLogin) || TextUtils.isEmpty(passwordTextLogin)){
//                Toast.makeText(this, "Field can not be empty", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
////            progressBar.visibility = View.VISIBLE
//
//            auth.signInWithEmailAndPassword(emailTextLogin, passwordTextLogin)
//                .addOnCompleteListener{
//                    if(it.isSuccessful){
//                        if(Firebase.auth.currentUser?.isEmailVerified!!) {
//                            Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT)
//                                .show()
//                            startActivity(intent)
////                                progressBar.visibility = View.GONE
//                            finish()
//                        }else{
//                            Toast.makeText(this, "Please verify your email to continue.", Toast.LENGTH_SHORT).show()
//                        }
//                    } else{
//                        Toast.makeText(this, "Some error occurred" + it.exception?.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }
//
//        forgotPassword.setOnClickListener{
//            if(emailTextLogin == "") {
//                Toast.makeText(this, "Please enter email address...", Toast.LENGTH_SHORT).show()
//            }else{
//                auth.sendPasswordResetEmail(emailTextLogin)
//            }
//        }

        register.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}