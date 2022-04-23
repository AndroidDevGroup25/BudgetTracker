package com.example.budgettracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.budgettracker.fragments.SummaryFragment
import com.example.budgettracker.fragments.InsertFragment
import com.example.budgettracker.fragments.ScanFragment
import com.example.budgettracker.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottom_navigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager
        bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        // Setting itemSelectedListener for the items in the bottom navigation view.
        bottom_navigation.setOnItemSelectedListener { item ->

            var fragmentToShow: Fragment? = null
            when (item.itemId) {

                R.id.action_summary -> {
                    // Navigate to the Home Screen/ feed fragment
                    fragmentToShow = SummaryFragment()
                    Toast.makeText(this, "Summary", Toast.LENGTH_SHORT).show()
                }

                R.id.action_insert -> {
                    // Navigate to the Compose Screen
                    fragmentToShow = InsertFragment(bottom_navigation)
                    Toast.makeText(this, "Insert", Toast.LENGTH_SHORT).show()
                }

                R.id.action_scan -> {
                    // Navigate to the Profile Screen
                    fragmentToShow = ScanFragment()
                    Toast.makeText(this, "Scan", Toast.LENGTH_SHORT).show()
                }

                R.id.action_profile -> {
                    // Navigate to the Profile Screen
                    fragmentToShow = ProfileFragment()
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                }
            }
            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow)
                    .commit()
            }

            // returning true signifies that we have handled this user interaction on the item
            true
        }

        // Set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_summary
    }
}