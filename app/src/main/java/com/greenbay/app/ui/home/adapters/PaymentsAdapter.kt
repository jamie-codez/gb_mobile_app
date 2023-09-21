package com.greenbay.app.ui.home.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.greenbay.app.databinding.PaymentItemBinding
import com.greenbay.app.ui.home.models.Payment
import java.text.SimpleDateFormat
import java.util.Locale

class PaymentsAdapter(private var payments: List<Payment>) :
    RecyclerView.Adapter<PaymentsAdapter.PaymentsViewHolder>() {
    private lateinit var onPaymentClickListener: OnPaymentClickListener

    interface OnPaymentClickListener {
        fun onPaymentClick(position: Int)
    }

    fun setPayments(payments: List<Payment>) {
        this.payments = payments
        notifyDataSetChanged()
    }

    fun setOnPaymentClickListener(listener: OnPaymentClickListener) {
        onPaymentClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        return PaymentsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                com.greenbay.app.R.layout.payment_item,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
        holder.bind(payments[position])
    }

    override fun getItemCount() = payments.size

    class PaymentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PaymentItemBinding.bind(view)

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(payment: Payment) {
            binding.paymentTitleTv.text = payment.title
            binding.paymentAmountTv.text = "Amount: KES. ${payment.amount.toString()}"
            binding.paymentDescriptionTv.text = payment.description
//            binding.paymentTimeTv.text =
//                SimpleDateFormat(
//                    "dd/MM/yyyy HH:mm:ss",
//                    Locale.getDefault()
//                ).format(payment.dateCreated)
            if (payment.verified == true) {
                binding.paymentDot.setBackgroundColor(binding.root.context.getColor(com.greenbay.app.R.color.green))
            } else {
                binding.paymentDot.setBackgroundColor(binding.root.context.getColor(com.greenbay.app.R.color.red))
            }
        }
    }
}