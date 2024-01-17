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
import com.example.tentanganak.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private val db = Firebase.firestore
    private val PREF = "account"
    private lateinit var sharepreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharepreferences = getSharedPreferences(PREF,Context.MODE_PRIVATE)

        binding.daftar.setOnClickListener {
            val nama = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            val email = binding.edEmail.text.toString()
            val data = hashMapOf(
                "email" to email,
                "password" to password,
                "nama" to nama
            )
            if (!nama.equals("") && !password.equals("") && !email.equals("")){
                db.collection("account").document(email)
                    .set(data)
                    .addOnSuccessListener {
                        val editor = sharepreferences.edit()
                        editor.putString("email",email)
                        editor.putString("nama",nama)
                        editor.putBoolean("stats",true)
                        editor.putString("pass",password)
                        editor.apply()
                        startActivity(Intent(this@SignUpActivity,MainActivity::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@SignUpActivity,"Gagal Mendaftar Akun",Toast.LENGTH_SHORT).show()
                    }
            }else{
                Toast.makeText(this@SignUpActivity,"Pastikan Data Terisi",Toast.LENGTH_SHORT).show()
            }
        }

    }
}