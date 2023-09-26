package com.example.notessharingapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.notessharingapp.databinding.LoginActivityBinding

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

        register.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}