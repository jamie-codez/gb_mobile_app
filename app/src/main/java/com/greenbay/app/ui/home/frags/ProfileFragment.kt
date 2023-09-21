package com.greenbay.app.ui.home.frags

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.greenbay.app.R
import com.greenbay.app.databinding.FragmentLandingBinding
import com.greenbay.app.databinding.FragmentProfileBinding
import com.greenbay.app.models.AppUserResponse
import com.greenbay.app.network.GreenBayService
import com.greenbay.app.network.RetrofitInstance
import com.greenbay.app.ui.auth.viewmodels.AuthViewModel
import com.greenbay.app.ui.home.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by lazy {
         AuthViewModel(requireActivity().application)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = requireActivity().getSharedPreferences(
            requireActivity().getString(R.string.app_name),
            MODE_PRIVATE
        )
        val accessToken = prefs.getString("accessToken", "")
        Log.i("ProfileFragment", "onViewCreated: $accessToken")
        val email = prefs.getString("email", "")
        // get user
        val retrofitInstance = RetrofitInstance.getRetrofitInstance()
        val service = retrofitInstance.create(GreenBayService::class.java)
        if (email == null) {
            Snackbar.make(binding.root, "Error getting user", Snackbar.LENGTH_LONG).show()
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            service.getUser(accessToken!!, email).enqueue(object : retrofit2.Callback<AppUserResponse> {
                override fun onResponse(
                    call: retrofit2.Call<AppUserResponse>,
                    response: retrofit2.Response<AppUserResponse>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()?.payload
                        if (user != null) {
                            with(prefs.edit()) {
                                putString("userId", user.id)
                                putString("username", user.username)
                                putString("firstName", user.firstName)
                                putString("lastName", user.lastName)
                                putString("email", user.email)
                                putString("phone", user.phone)
                                putString("idNumber", user.idNumber)
                                putString("profileImage", user.profileImage)
                                putString("roles", user.roles.toString())
                                putString("password", user.password)
                                putBoolean("verified", user.verified)
                                apply()
                            }
                            Log.i("ProfileFragment", "onViewCreated: $accessToken")
                            binding.profileNameTv.text = "Username: ${user.username}"
                            binding.profileFirstNameTv.text = "First name: ${user.firstName}"
                            binding.profileLastNameTv.text = "Last name: ${user.lastName}"
                            binding.profileEmailTv.text = "Email: ${user.email}"
                            binding.profilePhoneTv.text = "Phone: ${user.phone}"
                            binding.profileIdNoTv.text = "ID No.: ${user.idNumber}"
                        }else{
                            Snackbar.make(binding.root, "Error getting user", Snackbar.LENGTH_LONG).show()
                        }
                    }else{
                        Snackbar.make(binding.root, "Error getting user", Snackbar.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<AppUserResponse>, t: Throwable) {
                    Snackbar.make(binding.root, "Error getting user", Snackbar.LENGTH_LONG).show()
                }

            })
        }

//        if (email != null) {
//            viewModel.getUser(accessToken,email).observe(viewLifecycleOwner) {
//                if (it == null) {
//                    Snackbar.make(binding.root, "Error getting user", Snackbar.LENGTH_LONG).show()
//                    return@observe
//                }
//                with(prefs.edit()) {
//                    putString("userId", it.id)
//                    putString("username", it.username)
//                    putString("firstName", it.firstName)
//                    putString("lastName", it.lastName)
//                    putString("email", it.email)
//                    putString("phone", it.phone)
//                    putString("idNumber", it.idNumber)
//                    putString("profileImage", it.profileImage)
//                    putString("roles", it.roles.toString())
//                    putString("password", it.password)
//                    putBoolean("verified", it.verified)
//                    apply()
//                }
//                Log.i("ProfileFragment", "onViewCreated: $accessToken")
//                binding.profileNameTv.text = "Username: ${it.username}"
//                binding.profileFirstNameTv.text = "First name: ${it.firstName}"
//                binding.profileLastNameTv.text = "Last name: ${it.lastName}"
//                binding.profileEmailTv.text = "Email: ${it.email}"
//                binding.profilePhoneTv.text = "Phone: ${it.phone}"
//                binding.profileIdNoTv.text = "ID No.: ${it.idNumber}"
//            }
//        }else{
//            Snackbar.make(binding.root, "Error getting user", Snackbar.LENGTH_LONG).show()
//        }
    }

}