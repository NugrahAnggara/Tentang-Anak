package com.example.tentanganak.menu

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.tentanganak.R
import com.example.tentanganak.databinding.FragmentProfileBinding
import com.example.tentanganak.starting.SignInActivity
import com.example.tentanganak.starting.SplashScreen
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ProfileFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("account", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "")
        val password = sharedPreferences.getString("pass","")

        db.collection("account").document(email!!)
            .get()
            .addOnSuccessListener {
                binding.tvNama.text = it.data!!.get("nama").toString()
                binding.tvEmail.text = it.data!!.get("email").toString()
            }

        binding.btnEdit.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog)
            dialog.show()
            val submit = dialog.findViewById<Button>(R.id.submit)
            val ednama = dialog.findViewById<EditText>(R.id.nama)
            submit.setOnClickListener {
                val nama = ednama.text.toString()
                if (!nama.isEmpty()){
                    val data = hashMapOf(
                        "nama" to nama,
                        "email" to email,
                        "password" to password,
                    )

                    db.collection("account").document(email!!)
                        .set(data)
                        .addOnSuccessListener {
                            val editor = sharedPreferences.edit()
                            editor.putString("nama",ednama.text.toString())
                            editor.putBoolean("stats",true)
                            editor.apply()
                            dialog.dismiss()
                        }
                }else{
                    Toast.makeText(requireActivity(),"Pastikan Data terisi",Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.hapusAkun.setOnClickListener {
            db.collection("account").document(email!!)
                .delete()
                .addOnSuccessListener {
                    val editor = sharedPreferences.edit()
                    editor.clear()
                    editor.apply()
                    startActivity(Intent(requireActivity(), SplashScreen::class.java))
                }
        }

        binding.logout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(requireActivity(), SplashScreen::class.java))
        }
    }

}