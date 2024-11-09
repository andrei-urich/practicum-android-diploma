package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.R

class RootActivity : AppCompatActivity(R.layout.activity_root) {

    private lateinit var bottomNavView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment

        val navController = navHostFragment.navController
        bottomNavView = findViewById(R.id.bnView)

    }
}

