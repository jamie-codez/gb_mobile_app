package com.greenbay.app.ui.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.navigation.fragment.NavHostFragment
import com.greenbay.app.R
import com.greenbay.app.databinding.ActivityMainBinding
import com.greenbay.app.ui.auth.viewmodels.AuthViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val authViewModel by lazy {
        AuthViewModel(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(binding.root)
        val preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        val accessToken = preferences.getString("accessToken", "")
        val isLoggedIn = !accessToken.isNullOrEmpty()
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.splashHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController
        if (isLoggedIn) {
            Handler(Looper.myLooper() ?: Looper.getMainLooper()).postDelayed({
                navController.navigate(R.id.action_splashFragment_to_homeActivity)
            }, 2000)
        } else {
            Handler(Looper.myLooper() ?: Looper.getMainLooper()).postDelayed({
                navController.navigate(R.id.action_splashFragment_to_loginFragment)
            }, 2000)
        }
    }
}