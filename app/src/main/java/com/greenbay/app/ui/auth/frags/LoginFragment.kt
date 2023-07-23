package com.greenbay.app.ui.auth.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.greenbay.app.R
import com.greenbay.app.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            binding.loginBtn.text="Please wait..."
            binding.progressBar.visibility = VISIBLE
            val email = binding.loginEmailEt.text.toString().trim()
            val password = binding.loginPasswordEt.text.toString().trim()
            if (email.isEmpty()) {
                binding.loginEmailEtl.error = "Email is required"
                binding.loginBtn.text="Login"
                binding.progressBar.visibility = INVISIBLE
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.loginPasswordEtl.error = "Password is required"
                binding.loginBtn.text="Login"
                binding.progressBar.visibility = INVISIBLE
                return@setOnClickListener
            }
        }
    }

}