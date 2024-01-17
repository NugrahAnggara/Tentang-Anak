package com.example.tentanganak.starting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tentanganak.MainActivity
import com.example.tentanganak.R
import com.example.tentanganak.databinding.ActivitySignInBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val db = Firebase.firestore
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("account",Context.MODE_PRIVATE)
        binding.login.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            if (!email.equals("") && !password.equals("")){
                db.collection("account")
                    .document(email)
                    .get()
                    .addOnSuccessListener {
                        val emailDb = it.get("email")
                        val passwordDb = it.get("password")
                        val namaDb = it.get("nama")
                        if (email.equals(emailDb) && password.equals(passwordDb)){
                            val editor = sharedPreferences.edit()
                            editor.putString("email",email)
                            editor.putString("nama",namaDb.toString())
                            editor.putBoolean("stats",true)
                            editor.putString("pass",password)
                            editor.apply()
                            startActivity(Intent(this@SignInActivity,MainActivity::class.java))
                        }else{
                            Toast.makeText(this@SignInActivity,"akun tidak terdaftar",Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@SignInActivity,"akun tidak terdaftar",Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}