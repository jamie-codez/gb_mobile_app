package com.greenbay.app.ui.home.frags

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.greenbay.app.R
import com.greenbay.app.databinding.FragmentProfileBinding
import com.greenbay.app.databinding.ProfileUpdateFormBinding
import com.greenbay.app.models.AppUser
import com.greenbay.app.models.AppUserResponse
import com.greenbay.app.models.Roles
import com.greenbay.app.network.GreenBayService
import com.greenbay.app.network.RetrofitInstance
import com.greenbay.app.ui.auth.viewmodels.AuthViewModel
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
                                user.verified?.let { putBoolean("verified", it) }
                                apply()
                            }
                            Log.i("ProfileFragment", "onViewCreated: $accessToken")
                            binding.profileNameTv.text = "Username: ${user.username}"
                            binding.profileFirstNameTv.text = "First name: ${user.firstName}"
                            binding.profileLastNameTv.text = "Last name: ${user.lastName}"
                            binding.profileEmailTv.text = "Email: ${user.email}"
                            binding.profilePhoneTv.text = "Phone: ${user.phone}"
                            binding.profileIdNoTv.text = "ID No.: ${user.idNumber}"
                        } else {
                            Snackbar.make(binding.root, "Error getting user", Snackbar.LENGTH_LONG)
                                .show()
                        }
                    } else {
                        Snackbar.make(binding.root, "Error getting user", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<AppUserResponse>, t: Throwable) {
                    Snackbar.make(binding.root, "Error getting user", Snackbar.LENGTH_LONG).show()
                }

            })
        }
        binding.editProfileFab.setOnClickListener {
            val inflater = LayoutInflater.from(requireContext())
                .inflate(R.layout.profile_update_form, binding.root, false)
            val updateBinding = ProfileUpdateFormBinding.bind(inflater)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
                .setTitle("Profile Update")
                .setView(updateBinding.root)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            updateBinding.profileUpdateBtn.setOnClickListener {
                val username = updateBinding.usernameEt.text.toString().trim()
                val firstName = updateBinding.firstNameEt.text.toString().trim()
                val lastName = updateBinding.lastNameEt.text.toString().trim()
                val email = updateBinding.emailEt.text.toString().trim()
                val phone = updateBinding.phoneEt.text.toString().trim()
                val idNumber = updateBinding.idNoEt.text.toString().trim()
                val appUser: AppUser
                updateBinding.apply {
                    if (username.isEmpty()) {
                        usernameEtl.error = "Username is required"
                        return@setOnClickListener
                    }
                    if (firstName.isEmpty()) {
                        firstNameEtl.error = "First name is required"
                        return@setOnClickListener
                    }
                    if (lastName.isEmpty()) {
                        lastNameEtl.error = "Last name is required"
                        return@setOnClickListener
                    }
                    if (email.isEmpty()) {
                        emailEtl.error = "Email is required"
                        return@setOnClickListener
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailEtl.error = "Invalid email"
                        return@setOnClickListener
                    }
                    if (phone.isEmpty()) {
                        phoneEtl.error = "Phone is required"
                        return@setOnClickListener
                    }
                    if (idNumber.isEmpty()) {
                        idNoEtl.error = "ID No. is required"
                        return@setOnClickListener
                    }
                    val password = prefs.getString("password", "")
                    val roles = prefs.getString("roles", "")
                    appUser = AppUser(
                        username = username,
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        phone = phone,
                        idNumber = idNumber,
                        profileImage = "",
                        addedBy = "",
                        roles = Gson().fromJson(roles, Roles::class.java),
                        password = password!!,
                        verified = true,
                        addedOn = null
                    )
                }
                viewModel.updateAppUser(requireActivity(), appUser).observe(viewLifecycleOwner) {
                    if (it != null) {
                        Snackbar.make(
                            binding.root,
                            "Profile updated successfully",
                            Snackbar.LENGTH_LONG
                        ).show()
                        alertDialog.dismiss()
                    } else {
                        Snackbar.make(
                            binding.root,
                            "Failed to update profile",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

}