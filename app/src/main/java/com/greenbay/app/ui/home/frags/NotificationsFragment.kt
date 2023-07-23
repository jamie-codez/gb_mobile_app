package com.greenbay.app.ui.home.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenbay.app.R
import com.greenbay.app.databinding.FragmentLandingBinding
import com.greenbay.app.databinding.FragmentNotificationsBinding
import com.greenbay.app.ui.home.adapters.NotificationsAdapter
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