package com.example.medicareconnect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//
//        // Liên kết BottomNavigationView với NavController
//        navView.setupWithNavController(navController)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavView.setupWithNavController(navController)
    }
}
