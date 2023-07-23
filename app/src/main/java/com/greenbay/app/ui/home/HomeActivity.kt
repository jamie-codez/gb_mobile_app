package com.greenbay.app.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.greenbay.app.R
import com.greenbay.app.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}