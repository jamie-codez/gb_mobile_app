package com.greenbay.app.ui.home.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser().observe(viewLifecycleOwner) {
            binding.profileNameTv.text = it.username
            binding.profileFirstNameTv.text = it.firstName
            binding.profileLastNameTv.text = it.lastName
            binding.profileEmailTv.text = it.email
            binding.profilePhoneTv.text = it.phone
            binding.profileIdNoTv.text = it.idNumber
        }
    }

}