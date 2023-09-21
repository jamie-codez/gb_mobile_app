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
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var navController: NavController
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
        navController = navHostFragment.navController
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
        binding.forgotPasswordTv.setOnClickListener {
            val emailEditText = EditText(requireContext()).apply {
                hint= "Email Address"
            }
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Password Reset")
                setMessage("Enter your email address to reset your password")
                setView(emailEditText)
                setPositiveButton("SUBMIT"){ dialog,which->
                    val progressDialog = AlertDialog.Builder(requireContext()).apply {
                        setTitle("Please wait")
                        setMessage("Sending password reset link")
                        setCancelable(false)
                    }.create()
                    progressDialog.show()
                    val email = emailEditText.text.toString().trim()
                    if (email.isEmpty()) {
                        progressDialog.dismiss()
                        emailEditText.error = "Email is required"
                        return@setPositiveButton
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        progressDialog.dismiss()
                        emailEditText.error = "Invalid email address"
                        return@setPositiveButton
                    }
                    viewModel.resetPassword(email).observe(viewLifecycleOwner){
                        if (it.status== 200){
                            progressDialog.dismiss()
                            Snackbar.make(binding.root,"Password reset link sent to $email",Snackbar.LENGTH_LONG).show()
                        }else{
                            progressDialog.dismiss()
                            Snackbar.make(binding.root,"Error resetting password",Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }.create().show()
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
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
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
                            if (loginResponse!!.success) {
                                binding.loginBtn.isEnabled = true
                                viewModel.email = email
                                binding.loginBtn.text = getString(R.string.login)
                                binding.progressBar.visibility = INVISIBLE
                                navController.navigate(R.id.action_loginFragment_to_homeActivity)
                            } else {
                                binding.loginBtn.isEnabled = true
                                binding.loginBtn.text = getString(R.string.login)
                                binding.progressBar.visibility = INVISIBLE
                            }
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                        Log.i("LoginFragment", "onFailure: ${t.message}")
                    }
                })
        }
    }
}