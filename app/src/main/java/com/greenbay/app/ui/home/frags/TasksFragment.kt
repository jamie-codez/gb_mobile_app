package com.greenbay.app.ui.home.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenbay.app.databinding.FragmentTasksBinding
import com.greenbay.app.ui.home.adapters.TasksAdapter
import com.greenbay.app.ui.home.viewmodels.HomeViewModel


class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private val viewModel by lazy {
        HomeViewModel(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tasksRecyclerView = binding.tasksRv
        tasksRecyclerView.setHasFixedSize(true)
        tasksRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        tasksRecyclerView.adapter = TasksAdapter(listOf())
        viewModel.getTasks().observe(viewLifecycleOwner) {
            (tasksRecyclerView.adapter as TasksAdapter).setTasks(it)
        }
    }

}