package com.greenbay.app.ui.home.frags

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.greenbay.app.R
import com.greenbay.app.databinding.FragmentLandingBinding
import com.greenbay.app.databinding.FragmentProfileBinding
import com.greenbay.app.ui.auth.viewmodels.AuthViewModel
import com.greenbay.app.ui.home.viewmodels.HomeViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by lazy {
         AuthViewModel(requireActivity().application)
    }
    private val userId by lazy {
        requireActivity().getSharedPreferences(
            requireActivity().getString(R.string.app_name),
            MODE_PRIVATE
        ).getString("userId", "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (userId.isNullOrEmpty()) {
            Snackbar.make(binding.root, "Error getting user", Snackbar.LENGTH_LONG).show()
            return
        }
        viewModel.getUser(userId!!).observe(viewLifecycleOwner) {
            if (it == null) {
                Snackbar.make(binding.root, "Error getting user", Snackbar.LENGTH_LONG).show()
                return@observe
            }
            binding.profileNameTv.text = it.username
            binding.profileFirstNameTv.text = it.firstName
            binding.profileLastNameTv.text = it.lastName
            binding.profileEmailTv.text = it.email
            binding.profilePhoneTv.text = it.phone
            binding.profileIdNoTv.text = it.idNumber
        }
    }

}