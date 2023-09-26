package com.example.notessharingapp

import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.notessharingapp.databinding.SignupActivityBinding

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
    }
}