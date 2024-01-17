package com.example.tentanganak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tentanganak.menu.HomeFragment
import com.example.tentanganak.menu.ProfileFragment
import com.example.tentanganak.menu.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment())
        val botNav = findViewById<BottomNavigationView>(R.id.botnav)
        botNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.search ->{
                    loadFragment(SearchFragment())
                    true
                }

                R.id.profile ->{
                    loadFragment(ProfileFragment())
                    true
                }

                else -> {
                    loadFragment(HomeFragment())
                    true
                }
            }
        }
    }


    private fun loadFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container,fragment)
            .commit()
    }
}