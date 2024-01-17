package com.example.tentanganak.items_menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tentanganak.DetailActivityItem
import com.example.tentanganak.R
import com.example.tentanganak.adapter.InfoAdapter
import com.example.tentanganak.databinding.ActivityArtikelBinding
import com.example.tentanganak.model.ModelModul
import com.google.api.Distribution.BucketOptions.Linear
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Suppress("DEPRECATION")
class ArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtikelBinding
    private val db = Firebase.firestore
    private val list = ArrayList<ModelModul>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvModul.layoutManager = LinearLayoutManager(this@ArtikelActivity)

        db.collection("modul")
            .get()
            .addOnSuccessListener { datas ->
                for (data in datas) {
                    val judul = data.data!!.get("judul")
                    val avatar = data.data!!.get("avatar")
                    val desc = data.data!!.get("desc")
                    list.add(ModelModul(judul.toString(), desc.toString(), avatar.toString()))
                }
                val adapter = InfoAdapter(list)
                adapter.setOnClickListener(object : InfoAdapter.OnClickListener{
                    override fun onClick(position: Int, model: ModelModul) {
                        val intent = Intent(this@ArtikelActivity,DetailActivityItem::class.java)
                        intent.putExtra("data",model)
                        startActivity(intent)
                    }
                })
                binding.rvModul.adapter = adapter
            }
            .addOnFailureListener {
                Toast.makeText(this@ArtikelActivity, "Gagal Memuat Artikel", Toast.LENGTH_SHORT)
                    .show()
            }

        binding.back.setOnClickListener {
            onBackPressed()
        }

    }
}