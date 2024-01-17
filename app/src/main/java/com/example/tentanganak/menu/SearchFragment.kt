package com.example.tentanganak.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tentanganak.DetailActivityItem
import com.example.tentanganak.R
import com.example.tentanganak.adapter.InfoAdapter
import com.example.tentanganak.databinding.FragmentSearchBinding
import com.example.tentanganak.model.ModelModul
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<ModelModul>()
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSearch.layoutManager = LinearLayoutManager(requireActivity())

        val search = view.findViewById<SearchView>(R.id.searchbar)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                db.collection("modul")
                    .whereEqualTo("judul",p0)
                    .get()
                    .addOnSuccessListener {datas->
                        list.clear()
                        for (data in datas){
                            val judul = data.data["judul"]
                            val avatar = data.data["avatar"]
                            val desc = data.data["desc"]
                            list.add(ModelModul(judul.toString(),desc.toString(),avatar.toString()))
                        }
                        val adapter = InfoAdapter(list)
                        adapter.setOnClickListener(object:InfoAdapter.OnClickListener{
                            override fun onClick(position: Int, model: ModelModul) {
                                val intent = Intent(requireActivity(),DetailActivityItem::class.java)
                                intent.putExtra("data",model)
                                startActivity(intent)
                            }
                        })
                        binding.rvSearch.adapter = adapter
                    }
                    .addOnFailureListener {

                    }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
               return true
            }
        })
    }
}