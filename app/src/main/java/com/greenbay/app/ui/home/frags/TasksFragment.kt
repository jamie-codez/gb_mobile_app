package com.greenbay.app.ui.home.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenbay.app.R
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
        binding.apply {
            tasksProgressBar.visibility = View.VISIBLE
            tasksLoadingTv.visibility = View.VISIBLE
            tasksRv.visibility = View.GONE
            noItemsIvTasks.visibility = View.GONE
            noItemsTvTasks.visibility = View.GONE
            addTaskFab.setOnClickListener {
//                val dialog = LayoutInflater.from(requireContext()).inflate(R.layout.add_task_dialog, null)
            }
        }
        val tasksRecyclerView = binding.tasksRv
        val tasksAdapter = TasksAdapter(listOf())
        tasksRecyclerView.setHasFixedSize(true)
        tasksRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        tasksRecyclerView.adapter = tasksAdapter
        viewModel.getTaskz().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.apply {
                    tasksProgressBar.visibility = GONE
                    tasksLoadingTv.visibility = GONE
                    tasksRv.visibility = GONE
                    noItemsIvTasks.visibility = VISIBLE
                    noItemsTvTasks.visibility = VISIBLE
                }
            } else {
                binding.apply {
                    tasksProgressBar.visibility = GONE
                    tasksLoadingTv.visibility = GONE
                    tasksRv.visibility = VISIBLE
                    noItemsIvTasks.visibility = GONE
                    noItemsTvTasks.visibility = GONE
                }
                tasksAdapter.setTasks(it)
            }
        }
    }

}