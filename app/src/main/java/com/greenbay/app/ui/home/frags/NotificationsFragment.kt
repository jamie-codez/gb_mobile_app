package com.greenbay.app.ui.home.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.greenbay.app.R
import com.greenbay.app.databinding.AddNotificationDialogBinding
import com.greenbay.app.databinding.FragmentLandingBinding
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
        binding.addNotificationFab.setOnClickListener {
            val addNotifView =
                layoutInflater.inflate(R.layout.add_notification_dialog, view as ViewGroup?, false)
            val binding = AddNotificationDialogBinding.bind(addNotifView)
            val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Add Notification")
            alertDialogBuilder.setView(binding.root)
            alertDialogBuilder.setPositiveButton("Add") { _, _ ->
                val title = binding.communicationDialogTitleEt.text.toString().trim()
                val description = binding.communicationDialogDescriptionEt.text.toString().trim()
                if (title.isEmpty()) {
                    binding.communicationDialogDescriptionEtl.error = "Title is required"
                    return@setPositiveButton
                }
                if (description.isEmpty()) {
                    binding.communicationDialogDescriptionEtl.error = "Message is required"
                    return@setPositiveButton
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
                        alertDialogBuilder.create().dismiss()
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
        val notificationRecyclerView = binding.notificationsRv
        notificationRecyclerView.setHasFixedSize(true)
        notificationRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        notificationRecyclerView.adapter = NotificationsAdapter(listOf())
        viewModel.getCommunications().observe(viewLifecycleOwner) {
            (notificationRecyclerView.adapter as NotificationsAdapter).setNotifications(it)
        }
    }

}
