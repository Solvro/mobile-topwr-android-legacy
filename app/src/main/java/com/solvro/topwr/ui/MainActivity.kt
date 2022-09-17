package com.solvro.topwr.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.solvro.topwr.R
import com.solvro.topwr.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
    }

    private fun initNavigation() {
        navController = findNavController(R.id.fragment_nav_host)
        binding.bottomNavigation.setupWithNavController(navController)
        // Temporary solution
        // TODO: Handle multiple backstack
        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item, navController, false)
                true
            }
        }
    }

    fun changeBottomNavView(page: BottomNavPage) {
        val newPage = when (page) {
            BottomNavPage.HomePage -> R.id.homeFragment
            BottomNavPage.MapPage -> R.id.mapFragment
            BottomNavPage.DepartmentsPage -> R.id.departmentsFragment
            BottomNavPage.ScienceClubsPage -> R.id.scienceClubsFragment
            BottomNavPage.FAQPage -> R.id.faqFragment
        }
        binding.bottomNavigation.selectedItemId = newPage
    }

    enum class BottomNavPage {
        HomePage, MapPage, DepartmentsPage, ScienceClubsPage, FAQPage
    }
}