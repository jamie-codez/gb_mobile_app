package com.greenbay.app.ui.home.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenbay.app.R
import com.greenbay.app.databinding.FragmentLandingBinding
import com.greenbay.app.databinding.FragmentPaymentsBinding
import com.greenbay.app.ui.home.adapters.PaymentsAdapter
import com.greenbay.app.ui.home.viewmodels.HomeViewModel


class PaymentsFragment : Fragment() {
    private lateinit var binding: FragmentPaymentsBinding
    private val viewModel by lazy {
        HomeViewModel(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val paymentsRecyclerView = binding.paymentsRv
        paymentsRecyclerView.setHasFixedSize(true)
        paymentsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        paymentsRecyclerView.adapter = PaymentsAdapter(listOf())
        viewModel.getPayments().observe(viewLifecycleOwner) {
            (paymentsRecyclerView.adapter as PaymentsAdapter).setPayments(it)
        }
    }

}