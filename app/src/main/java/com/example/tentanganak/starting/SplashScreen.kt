package com.example.tentanganak.starting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.tentanganak.MainActivity
import com.example.tentanganak.R
import com.example.tentanganak.databinding.ActivitySplashScreenBinding

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("account",Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("stats",false) == true){
            startActivity(Intent(this@SplashScreen,MainActivity::class.java))
        }

        binding.daftar.setOnClickListener {
            val moveToSignUp = Intent(this@SplashScreen,SignUpActivity::class.java)
            startActivity(moveToSignUp)
        }

        binding.masuk.setOnClickListener {
            val moveToSignIn = Intent(this@SplashScreen,SignInActivity::class.java)
            startActivity(moveToSignIn)
        }
    }
}