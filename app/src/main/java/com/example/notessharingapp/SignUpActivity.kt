package com.example.notessharingapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.notessharingapp.databinding.SignupActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: SignupActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize Animations

        var fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        var bottom_down = AnimationUtils.loadAnimation(this, R.anim.bottom_down)

        // setting the bottom down animation on top layout

        binding.topLinearLayout.animation = bottom_down

        // creating handler for other layouts

        var handler = Handler()

        var runnable = Runnable {
            // set fade in animation for other layouts

            binding.textView1.animation = fade_in
            binding.metaLogoCardView.animation = fade_in
            binding.cardView.animation = fade_in
            binding.googleCardview.animation = fade_in
            binding.LogIn1.animation = fade_in
            binding.textView2.animation = fade_in
        }

        handler.postDelayed(runnable, 1000)

        val LogIn = findViewById<LinearLayout>(R.id.LogIn1)

        val email: EditText = findViewById(R.id.Email)
        val password: EditText = findViewById(R.id.Password)
        val confirmPassword: EditText = findViewById(R.id.ConfirmPassword)
        val signUpButton: AppCompatButton = findViewById(R.id.SignUpButton)

//        val progressbar: ProgressBar = findViewById(R.id.progressBar)

//        var auth = FirebaseAuth.getInstance()
//
//        var emailText = email.text.toString()
//        var passwordText = password.text.toString()
//        var confirmPasswordText = confirmPassword.text.toString()
//
//        val i = Intent(this , LoginActivity::class.java)
//
////        val actionCodeSettings = actionCodeSettings {
////            // URL you want to redirect back to. The domain (www.example.com) for this
////            // URL must be whitelisted in the Firebase Console.
////            url = "https://www.example.com/finishSignUp?cartId=1234"
////            // This must be true
////            handleCodeInApp = true
////            setIOSBundleId("com.example.ios")
////            setAndroidPackageName(
////                "com.example.android",
////                true, // installIfNotAvailable
////                "12", // minimumVersion
////            )
////        }
//
//        signUpButton.setOnClickListener {
//            auth = FirebaseAuth.getInstance()
//            emailText = email.text.toString()
//            passwordText = password.text.toString()
//            confirmPasswordText = confirmPassword.text.toString()
//
//            // for actual checking of the data like these(email pattern) we use Regex -> Regular Expression
//
//            if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText)|| TextUtils.isEmpty(confirmPasswordText)){
//                Toast.makeText(this, "Field can not be empty", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            if (passwordText.length < 6){
//                Toast.makeText(this, "Please enter a password with at least 6 characters", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            if (passwordText != confirmPasswordText){
//                Toast.makeText(this, "Passwords do not match \n Please check...", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
////            progressbar.visibility = View.VISIBLE
//
//            auth.createUserWithEmailAndPassword(emailText, passwordText)
//                .addOnCompleteListener{task ->              // This is a type of lambda function where we can define our own variable for use or we can use 'it' keyword.
//                    if(task.isSuccessful){
//                        Toast.makeText(this, "Registered Successfully..", Toast.LENGTH_SHORT).show()
//                        auth.currentUser?.sendEmailVerification()
//                            ?.addOnCompleteListener{
//                                Toast.makeText(this, "Verification Email Sent...", Toast.LENGTH_LONG).show()
//                                Toast.makeText(this, "Please verify to login..", Toast.LENGTH_LONG).show()
//                                startActivity(i)
////                                progressbar.visibility = View.GONE
//                                finish()
//                            }
//
////                        auth.currentUser?.sendEmailVerification()
////                        if(auth.currentUser?.isEmailVerified!!){
////                        }
//                    } else{
//                        Toast.makeText(this, "Something went wrong.." + task.exception?.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//        }

        LogIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}