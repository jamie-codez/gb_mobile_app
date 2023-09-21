package com.greenbay.app.ui.home.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.greenbay.app.R
import com.greenbay.app.databinding.AddNotificationDialogBinding
import com.greenbay.app.databinding.FragmentNotificationsBinding
import com.greenbay.app.ui.home.adapters.NotificationsAdapter
import com.greenbay.app.ui.home.models.Communication
import com.greenbay.app.ui.home.viewmodels.HomeViewModel


class NotificationsFragment : Fragment() {
    private lateinit var binding: FragmentNotificationsBinding
    private val viewModel: HomeViewModel by lazy {
        HomeViewModel(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = viewModel.email
        binding.apply {
            notificationsProgressBar.visibility = VISIBLE
            notificationsLoadingTv.visibility = VISIBLE
            notificationsRv.visibility = GONE
        }
        binding.addNotificationFab.setOnClickListener {
            val addNotifView =
                layoutInflater.inflate(R.layout.add_notification_dialog, view as ViewGroup?, false)
            val binding = AddNotificationDialogBinding.bind(addNotifView)
            val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Add Notification")
            alertDialogBuilder.setView(binding.root)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
            binding.communicationDialogTitleEt.doOnTextChanged { text, start, before, count ->
                if (text.toString().trim().isNotEmpty()) {
                    binding.communicationDialogTitleEtl.error = null
                } else {
                    binding.communicationDialogTitleEtl.error = "Title is required"
                }
            }
            binding.communicationDialogDescriptionEt.doOnTextChanged { text, start, before, count ->
                if (text.toString().trim().isNotEmpty()) {
                    binding.communicationDialogDescriptionEtl.error = null
                } else {
                    binding.communicationDialogDescriptionEtl.error = "Message is required"
                }
            }
            binding.dialogAddBtn.setOnClickListener {
                val title = binding.communicationDialogTitleEt.text.toString().trim()
                val description = binding.communicationDialogDescriptionEt.text.toString().trim()
                if (title.isEmpty()) {
                    binding.communicationDialogDescriptionEtl.error = "Title is required"
                    return@setOnClickListener
                }
                if (description.isEmpty()) {
                    binding.communicationDialogDescriptionEtl.error = "Message is required"
                    return@setOnClickListener
                }
                val communication =
                    Communication(
                        title = title,
                        description = description,
                        to = "admin",
                        createdBy = email
                    )
                viewModel.createCommunication(communication).observe(viewLifecycleOwner) {
                    if (it.id != null) {
                        Snackbar.make(
                            binding.root,
                            "Notification added successfully",
                            Snackbar.LENGTH_LONG
                        ).show()
                        alertDialog.dismiss()
                    } else {
                        Snackbar.make(
                            binding.root,
                            "Failed to add notification,try again",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        val notificationsAdapter = NotificationsAdapter(listOf())
        val notificationRecyclerView = binding.notificationsRv
        notificationRecyclerView.setHasFixedSize(true)
        notificationRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        notificationRecyclerView.adapter = notificationsAdapter
        viewModel.getCommunicationz().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.apply {
                    notificationsProgressBar.visibility = GONE
                    notificationsLoadingTv.visibility = GONE
                    notificationsRv.visibility = GONE
                    noItemsTvNotifications.visibility = VISIBLE
                    noItemsIvNotifications.visibility = VISIBLE
                }
            } else {
                binding.apply {
                    notificationsProgressBar.visibility = GONE
                    notificationsLoadingTv.visibility = GONE
                    notificationsRv.visibility = GONE
                    noItemsTvNotifications.visibility = GONE
                    noItemsIvNotifications.visibility = GONE
                    notificationsRv.visibility = VISIBLE
                }
                notificationsAdapter.setNotifications(it)
            }
        }
    }

}
