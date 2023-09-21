package com.greenbay.app.ui.auth.frags

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.greenbay.app.R
import com.greenbay.app.databinding.FragmentLoginBinding
import com.greenbay.app.models.LoginModel
import com.greenbay.app.models.LoginResponse
import com.greenbay.app.network.GreenBayService
import com.greenbay.app.network.RetrofitInstance
import com.greenbay.app.ui.auth.viewmodels.AuthViewModel
import com.greenbay.app.ui.auth.viewmodels.LoginResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
            it.isEnabled = false
            binding.progressBar.visibility= VISIBLE
            binding.loginBtn.text = getString(R.string.please_wait)
            val email = binding.loginEmailEt.text.toString().trim()
            val password = binding.loginPasswordEt.text.toString().trim()
            if (email.isEmpty()) {
                binding.loginBtn.isEnabled = true
                binding.loginEmailEtl.error = "Email is required"
                binding.loginBtn.text = getString(R.string.login)
                binding.progressBar.visibility = GONE
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.loginBtn.isEnabled = true
                binding.loginEmailEtl.error = "Invalid email address"
                binding.loginBtn.text = getString(R.string.login)
                binding.progressBar.visibility = GONE
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.loginBtn.isEnabled = true
                binding.loginPasswordEtl.error = "Password is required"
                binding.loginBtn.text = getString(R.string.login)
                binding.progressBar.visibility = INVISIBLE
                return@setOnClickListener
            }
            val results = login(email, password)
            if (results.success) {
                binding.loginBtn.isEnabled = true
                viewModel.email = email
                binding.loginBtn.text = getString(R.string.login)
                binding.progressBar.visibility = INVISIBLE
                navController.navigate(R.id.action_loginFragment_to_homeActivity)
            } else {
                it.isEnabled = true
                binding.loginBtn.text = getString(R.string.login)
                binding.progressBar.visibility = INVISIBLE
            }
        }
    }

    private fun login(email: String, password: String): LoginResult {
        val retrofitInstance = RetrofitInstance.getRetrofitInstance()
        val apiService = retrofitInstance.create(GreenBayService::class.java)
        var loginResponse: LoginResult? = null
        CoroutineScope(Dispatchers.IO).launch {
            apiService.login(LoginModel(email, password))
                .enqueue(object : retrofit2.Callback<LoginResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<LoginResponse>,
                        response: retrofit2.Response<LoginResponse>
                    ) {
                        if (response.code() == 200) {
                            val result = response.body()
                            val accessToken = result?.payload?.accessToken
                            val prefs = requireActivity().getSharedPreferences(
                                requireActivity().getString(R.string.app_name),
                                Context.MODE_PRIVATE
                            )
                            with(prefs.edit()) {
                                putString("accessToken", accessToken)
                                putString("email", email)
                                apply()
                            }
                            loginResponse = LoginResult(true, accessToken ?: "")
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                        Log.i("LoginFragment", "onFailure: ${t.message}")
                    }
                })
        }
        return loginResponse!!
    }
}