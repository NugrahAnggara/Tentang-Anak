package com.example.tentanganak.items_menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tentanganak.DetailActivityItem
import com.example.tentanganak.adapter.ModulAdapter
import com.example.tentanganak.databinding.ActivityModulBinding
import com.example.tentanganak.model.ModelModul
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Suppress("DEPRECATION")
class ModulActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModulBinding
    private val db = Firebase.firestore
    private val listItem = ArrayList<ModelModul>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModulBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvModul.layoutManager = LinearLayoutManager(this@ModulActivity)

        db.collection("modul")
            .get()
            .addOnSuccessListener {
                for (data in it) {
                    val nama = data.data.get("judul")
                    val avatar = data.data.get("avatar")
                    val desc = data.data.get("desc")
                    val dataAdapter = ModelModul(nama.toString(), desc.toString(), avatar.toString())
                    listItem.add(dataAdapter)
                }
                val adapter = ModulAdapter(listItem)
                adapter.setOnClickListener(object : ModulAdapter.OnClickListener {
                    override fun onClick(position: Int, model: ModelModul) {
                        val intent = Intent(this@ModulActivity,DetailActivityItem::class.java)
                        intent.putExtra("data",model)
                        startActivity(intent)
                    }
                })

                binding.rvModul.adapter = adapter
            }
            .addOnFailureListener {
                Toast.makeText(this@ModulActivity, "Data Gagal Dimuat", Toast.LENGTH_SHORT).show()
            }

        binding.back.setOnClickListener {
            onBackPressed()
        }

    }
}