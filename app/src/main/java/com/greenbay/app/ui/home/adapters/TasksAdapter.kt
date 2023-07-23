package com.greenbay.app.ui.home.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.greenbay.app.R
import com.greenbay.app.databinding.TaskItemBinding
import com.greenbay.app.ui.home.models.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TasksAdapter(private var tasks: List<Task>) :
    RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {
    private lateinit var onTaskClickListener: OnTaskClickListener

    interface OnTaskClickListener {
        fun onTaskClick(position: Int)
    }

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    fun setOnTaskClickListener(listener: OnTaskClickListener) {
        onTaskClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.task_item,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size

    class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = TaskItemBinding.bind(view)

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(task: Task) {
            binding.taskTitleTv.text = task.title
            binding.taskDescriptionTv.text = task.description
            binding.taskDescriptionTv.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(task.scheduledDate)
            binding.taskTimeTv.text =
                SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(task.createdOn)
            binding.paymentAmountTv.visibility = View.GONE
            binding.taskTimeTv.text =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(task.createdOn)
            if (task.status == true) {
                binding.taskDot.setBackgroundColor(binding.root.context.getColor(R.color.green))
            } else {
                binding.taskDot.setBackgroundColor(binding.root.context.getColor(R.color.red))
            }
        }
    }
}