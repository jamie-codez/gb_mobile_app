package com.greenbay.app.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.greenbay.app.R
import com.greenbay.app.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appBarConfiguration = androidx.navigation.ui.AppBarConfiguration(
            setOf(
                R.id.landingFragment,
                R.id.paymentsFragment,
                R.id.notificationsFragment,
                R.id.tasksFragment,
                R.id.profileFragment
            )
        )
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.homeFragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.homeBottomNav.setupWithNavController(navController)
    }
}