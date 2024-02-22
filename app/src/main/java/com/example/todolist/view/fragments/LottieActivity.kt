package com.example.todolist.view.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.todolist.R
import com.example.todolist.view.MainActivity

class LottieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@LottieActivity, MainActivity::class.java))
            finish()
        }, 3000)

    }
}