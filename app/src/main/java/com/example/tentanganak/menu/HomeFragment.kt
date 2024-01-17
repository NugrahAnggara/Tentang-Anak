package com.example.tentanganak.menu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tentanganak.DetailActivityItem
import com.example.tentanganak.R
import com.example.tentanganak.adapter.InfoAdapter
import com.example.tentanganak.databinding.FragmentHomeBinding
import com.example.tentanganak.items_menu.ArtikelActivity
import com.example.tentanganak.items_menu.ModulActivity
import com.example.tentanganak.model.ModelModul
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    private val list = ArrayList<ModelModul>()
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("account",Context.MODE_PRIVATE)
        val nama = sharedPreferences.getString("nama","")
        binding.tvNama.text = "Hi, $nama !"
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        db.collection("modul")
            .get()
            .addOnSuccessListener {datas ->
                for (data in datas){
                    val avatar = data.data!!.get("avatar")
                    val judul = data.data!!.get("judul")
                    val desc = data.data!!.get("desc")
                    list.add(ModelModul(judul.toString(),desc.toString(),avatar.toString()))
                }

                val adapter = InfoAdapter(list)
                adapter.setOnClickListener(object : InfoAdapter.OnClickListener{
                    override fun onClick(position: Int, model: ModelModul) {
                        val intent = Intent(requireActivity(),DetailActivityItem::class.java)
                        intent.putExtra("data",model)
                        startActivity(intent)
                    }
                })
                binding.recyclerView.adapter = adapter
            }

            .addOnFailureListener {
                Toast.makeText(requireActivity(),"Gagal memuat artikel",Toast.LENGTH_SHORT).show()
            }


        binding.modul.setOnClickListener {
            startActivity(Intent(requireActivity(),ModulActivity::class.java))
        }

        binding.konsultasi.setOnClickListener {
            Toast.makeText(requireActivity(),"Menu Sedang Dalam Pengembangan",Toast.LENGTH_SHORT).show()
        }

        binding.artikel.setOnClickListener {
            startActivity(Intent(requireActivity(),ArtikelActivity::class.java))
        }

    }
}