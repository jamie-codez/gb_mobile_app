package com.greenbay.app.ui.auth.frags

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.NavHostFragment
import com.greenbay.app.R
import com.greenbay.app.databinding.FragmentLoginBinding
import com.greenbay.app.models.LoginModel
import com.greenbay.app.ui.auth.viewmodels.AuthViewModel
import kotlin.math.log


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by lazy {
        AuthViewModel(requireActivity().application)
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
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.splash_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.loginEmailEt.doOnTextChanged { text, start, before, count ->
            if (text.toString().trim().isNotEmpty()) {
                binding.loginEmailEtl.error = null
            } else if (!Patterns.EMAIL_ADDRESS.matcher(text.toString().trim()).matches()) {
                binding.loginEmailEtl.error = "Invalid email address"
            } else {
                binding.loginEmailEtl.error = "Email is required"
            }
        }
        binding.loginPasswordEt.doOnTextChanged { text, start, before, count ->
            if (text.toString().trim().isNotEmpty()) {
                binding.loginPasswordEtl.error = null
            } else {
                binding.loginPasswordEtl.error = "Password is required"
            }
        }
        binding.loginBtn.setOnClickListener {
            binding.loginBtn.isEnabled = false
            binding.loginBtn.text = "Please wait..."
            binding.progressBar.visibility = VISIBLE
            val email = binding.loginEmailEt.text.toString().trim()
            val password = binding.loginPasswordEt.text.toString().trim()
            if (email.isEmpty()) {
                binding.loginBtn.isEnabled = true
                binding.loginEmailEtl.error = "Email is required"
                binding.loginBtn.text = "Login"
                binding.progressBar.visibility = INVISIBLE
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.loginBtn.isEnabled = true
                binding.loginEmailEtl.error = "Invalid email address"
                binding.loginBtn.text = "Login"
                binding.progressBar.visibility = INVISIBLE
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.loginBtn.isEnabled = true
                binding.loginPasswordEtl.error = "Password is required"
                binding.loginBtn.text = "Login"
                binding.progressBar.visibility = INVISIBLE
                return@setOnClickListener
            }
            viewModel.login(email, password).observe(viewLifecycleOwner) {
                if (it) {
                    binding.loginBtn.isEnabled = true
                    binding.loginBtn.text = "Login"
                    binding.progressBar.visibility = INVISIBLE
                    navController.navigate(R.id.action_loginFragment_to_homeActivity)
                } else {
                    binding.loginBtn.isEnabled = true
                    binding.loginBtn.text = "Login"
                    binding.progressBar.visibility = INVISIBLE
                }
            }
        }
    }

}