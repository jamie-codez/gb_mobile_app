package com.greenbay.app.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.greenbay.app.R
import com.greenbay.app.databinding.ActivityHomeBinding
import com.greenbay.app.ui.auth.MainActivity
import com.greenbay.app.ui.auth.viewmodels.AuthViewModel
import com.greenbay.app.ui.home.viewmodels.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: AuthViewModel by lazy {
        AuthViewModel(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.homeToolbar)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            viewModel.logout()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        return true
    }
}