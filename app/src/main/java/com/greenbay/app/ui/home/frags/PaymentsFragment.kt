package com.greenbay.app.ui.home.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.greenbay.app.R
import com.greenbay.app.databinding.CreatePaymentDialogBinding
import com.greenbay.app.databinding.FragmentPaymentsBinding
import com.greenbay.app.databinding.PayDialogBinding
import com.greenbay.app.models.STKPayload
import com.greenbay.app.ui.home.adapters.PaymentsAdapter
import com.greenbay.app.ui.home.models.Payment
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
        binding.apply {
            paymentsProgressBar.visibility = View.VISIBLE
            paymentsLoadingTv.visibility = View.VISIBLE
            paymentsRv.visibility = View.GONE
            noItemsIvPayments.visibility = View.GONE
            noItemsTvPayments.visibility = View.GONE
        }
        binding.makePaymentFab.setOnClickListener {
            val payView = layoutInflater.inflate(R.layout.pay_dialog, view as ViewGroup?, false)
            val binding = PayDialogBinding.bind(payView)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Make Payment")
            alertDialogBuilder.setView(binding.root)
            binding.dialogPayBtn.setOnClickListener {
                binding.apply {
                    paymentsProgressBar.visibility = View.VISIBLE
                    dialogPayBtn.isEnabled=false
                    dialogPayBtn.text = "Processing..."
                }
                val amnt = binding.dialogAmountEt.text.toString()
                if (amnt.isEmpty()) {
                    binding.apply {
                        dialogAmountEt.error = "Amount is required"
                        paymentsProgressBar.visibility = View.GONE
                        dialogPayBtn.isEnabled=true
                        dialogPayBtn.text = "Pay"
                    }
                    return@setOnClickListener
                }
                val amount = amnt.toDouble()
                if (amount < 1) {
                    binding.apply {
                        dialogAmountEt.error = "Amount must be greater than 0"
                        paymentsProgressBar.visibility = View.GONE
                        dialogPayBtn.isEnabled=true
                        dialogPayBtn.text = "Pay"
                    }
                    return@setOnClickListener
                }
                viewModel.getStkPush(STKPayload(ceil(amount).toInt())).observe(viewLifecycleOwner) {
                    if (it.status == 200) {
                        Snackbar.make(binding.root, "Payment request sent", Snackbar.LENGTH_LONG)
                            .show()
                        binding.apply {
                            paymentsProgressBar.visibility = View.GONE
                            dialogPayBtn.isEnabled=true
                            dialogPayBtn.text = "Pay"
                        }
                        alertDialogBuilder.create().dismiss()
                    } else {
                        Snackbar.make(binding.root, "Payment request failed", Snackbar.LENGTH_LONG)
                            .show()
                        binding.apply {
                            paymentsProgressBar.visibility = View.GONE
                            dialogPayBtn.isEnabled=true
                            dialogPayBtn.text = "Pay"
                        }
                    }
                }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        binding.addPaymentFab.setOnClickListener {
            Snackbar.make(binding.root, "Add payment clicked", Snackbar.LENGTH_LONG).show()
            val createPaymentView =
                layoutInflater.inflate(R.layout.create_payment_dialog, view as ViewGroup?, false)
            val binding = CreatePaymentDialogBinding.bind(createPaymentView)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Add Payment")
            alertDialogBuilder.setView(binding.root)
            binding.apply {
                dialogAddPaymentBtn.setOnClickListener {
                    val title = paymentDialogTitleEt.text.toString().trim()
                    val reference = paymentDialogReferenceEt.text.toString().trim()
                    val amount = paymentDialogAmountEt.text.toString().trim()
                    val description = descriptionDialogDescriptionEt.text.toString().trim()
                    if (title.isEmpty()) {
                        paymentDialogTitleEtl.error = "Title is required"
                        return@setOnClickListener
                    }
                    if (reference.isEmpty()) {
                        paymentDialogReferenceEtl.error = "Reference is required"
                        return@setOnClickListener
                    }
                    if (amount.isEmpty()) {
                        paymentDialogAmountEtl.error = "Amount is required"
                        return@setOnClickListener
                    }
                    if (description.isEmpty()) {
                        descriptionDialogDescriptionEtl.error = "Description is required"
                        return@setOnClickListener
                    }
                    val payment = Payment(
                        from = "admin",
                        title = title,
                        transactionCode = reference,
                        amount = amount,
                        description = description,
                        verified = false
                    )
                    viewModel.createPayment(payment).observe(viewLifecycleOwner) {
                        if (it.id != null) {
                            Snackbar.make(
                                binding.root,
                                "Payment added successfully",
                                Snackbar.LENGTH_LONG
                            ).show()
                            alertDialogBuilder.create().dismiss()
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Failed to add payment,try again",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        binding.makePaymentFab.setOnClickListener {
            val payView = layoutInflater.inflate(R.layout.pay_dialog, view as ViewGroup?, false)
            val binding = PayDialogBinding.bind(payView)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Make Payment")
            alertDialogBuilder.setView(binding.root)
            binding.dialogPayBtn.setOnClickListener {
                binding.apply {
                    paymentsProgressBar.visibility = View.VISIBLE
                    dialogPayBtn.isEnabled=false
                    dialogPayBtn.text = "Processing..."
                }
                val amnt = binding.dialogAmountEt.text.toString()
                val phoneNumber = binding.phoneNumberEt.text.toString()
                if (amnt.isEmpty()) {
                    binding.apply {
                        dialogAmountEt.error = "Amount is required"
                        paymentsProgressBar.visibility = View.GONE
                        dialogPayBtn.isEnabled=true
                        dialogPayBtn.text = "Pay"
                    }
                    return@setOnClickListener
                }
                val amount = amnt.toDouble()
                if (amount < 1) {
                    binding.apply {
                        dialogAmountEt.error = "Amount must be greater than 0"
                        paymentsProgressBar.visibility = View.GONE
                        dialogPayBtn.isEnabled=true
                        dialogPayBtn.text = "Pay"
                    }
                    return@setOnClickListener
                }
                val stkPayload = STKPayload(ceil(amount).toInt())
                if (phoneNumber.isNotEmpty()){
                    stkPayload.phone = phoneNumber
                }
                viewModel.getStkPush(stkPayload).observe(viewLifecycleOwner) {
                    if (it.status == 200) {
                        Snackbar.make(binding.root, "Payment request sent", Snackbar.LENGTH_LONG)
                            .show()
                        binding.apply {
                            paymentsProgressBar.visibility = View.GONE
                            dialogPayBtn.isEnabled=true
                            dialogPayBtn.text = "Pay"
                        }
                        alertDialogBuilder.create().dismiss()
                    } else {
                        Snackbar.make(binding.root, "Payment request failed", Snackbar.LENGTH_LONG)
                            .show()
                        binding.apply {
                            paymentsProgressBar.visibility = View.GONE
                            dialogPayBtn.isEnabled=true
                            dialogPayBtn.text = "Pay"
                        }
                    }
                }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        val paymentsAdapter = PaymentsAdapter(listOf())
        val paymentsRecyclerView = binding.paymentsRv
        paymentsRecyclerView.setHasFixedSize(true)
        paymentsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        paymentsRecyclerView.adapter = paymentsAdapter
        viewModel.getPaymentz().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.apply {
                    paymentsRv.visibility = View.GONE
                    paymentsProgressBar.visibility = View.GONE
                    paymentsLoadingTv.visibility = View.GONE
                    paymentsRv.visibility = View.GONE
                    noItemsIvPayments.visibility = View.VISIBLE
                    noItemsTvPayments.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    paymentsProgressBar.visibility = View.GONE
                    paymentsLoadingTv.visibility = View.GONE
                    paymentsRv.visibility = View.GONE
                    noItemsIvPayments.visibility = View.GONE
                    noItemsTvPayments.visibility = View.GONE
                    paymentsRv.visibility = View.VISIBLE
                    paymentsAdapter.setPayments(it)
                }
            }
        }
    }

}