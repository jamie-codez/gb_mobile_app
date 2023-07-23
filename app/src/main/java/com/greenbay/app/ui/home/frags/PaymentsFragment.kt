package com.greenbay.app.ui.home.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.greenbay.app.R
import com.greenbay.app.databinding.FragmentLandingBinding
import com.greenbay.app.databinding.FragmentPaymentsBinding
import com.greenbay.app.databinding.PayDialogBinding
import com.greenbay.app.ui.home.adapters.PaymentsAdapter
import com.greenbay.app.ui.home.viewmodels.HomeViewModel
import kotlin.math.ceil


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
        binding.makePaymentFab.setOnClickListener{
            val payView = layoutInflater.inflate(R.layout.pay_dialog, view as ViewGroup?, false)
            val binding = PayDialogBinding.bind(payView)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Make Payment")
            alertDialogBuilder.setView(binding.root)
            binding.dialogPayBtn.setOnClickListener {
                binding.dialogPayBtn.isEnabled = false
                binding.dialogPayBtn.text = "Processing..."
                val amnt = binding.dialogAmountEt.text.toString()
                if (amnt.isEmpty()) {
                    binding.dialogAmountEtl.error = "Amount is required"
                    binding.dialogPayBtn.isEnabled = true
                    binding.dialogPayBtn.text = "Pay"
                    return@setOnClickListener
                }
                val amount = amnt.toDouble()
                if (amount < 1) {
                    binding.dialogAmountEt.error = "Amount must be greater than 0"
                    binding.dialogPayBtn.isEnabled = true
                    binding.dialogPayBtn.text = "Pay"
                    return@setOnClickListener
                }
                viewModel.getStkPush(ceil(amount).toInt()).observe(viewLifecycleOwner){
                    if (it.status==200){
                        Snackbar.make(binding.root, "Payment request sent", Snackbar.LENGTH_LONG).show()
                        binding.dialogPayBtn.isEnabled = true
                        binding.dialogPayBtn.text = "Pay"
                        alertDialogBuilder.create().dismiss()
                    }else{
                        Snackbar.make(binding.root, "Payment request failed", Snackbar.LENGTH_LONG).show()
                        binding.dialogPayBtn.isEnabled = true
                        binding.dialogPayBtn.text = "Pay"
                    }
                }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
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