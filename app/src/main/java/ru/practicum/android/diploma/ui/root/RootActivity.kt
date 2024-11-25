package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.R

class RootActivity : AppCompatActivity(R.layout.activity_root) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bnView)
        bottomNavView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.filterSettingsFragment,
                R.id.areaFilterFragment,
                R.id.industryFilterFragment,
                R.id.vacancyDetailsFragment,
                R.id.countryFilterFragment,
                R.id.regionFilterFragment
                -> {
                    bottomNavView.visibility = View.GONE
                }

                else -> {
                    bottomNavView.visibility = View.VISIBLE
                }
            }
        }
    }
}
