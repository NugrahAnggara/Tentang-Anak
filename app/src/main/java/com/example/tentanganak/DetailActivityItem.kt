package com.example.tentanganak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.tentanganak.databinding.ActivityDetailItemBinding
import com.example.tentanganak.model.ModelModul

@Suppress("DEPRECATION")
class DetailActivityItem : AppCompatActivity() {
    private lateinit var binding : ActivityDetailItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<ModelModul>("data")
        binding.imageView3.setOnClickListener {
            onBackPressed()
        }

        val imageComponent = binding.ivGambar
        Glide.with(imageComponent)
            .load(data?.avatar)
            .into(imageComponent)

        binding.tvJudul.text = data?.judul.toString()
        binding.tvDesc.text = data?.desc.toString()

    }
}