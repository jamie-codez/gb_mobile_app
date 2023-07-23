package com.greenbay.app.ui.home.adapters

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.greenbay.app.databinding.NotificationItemBinding
import com.greenbay.app.ui.home.models.Communication
import java.util.Locale

class NotificationsAdapter(private val notifications: List<Communication>) :
    RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder>() {
    private lateinit var onNotificationClickListener: OnNotificationClickListener

    interface OnNotificationClickListener {
        fun onNotificationClick(position: Int)
    }

    fun setOnNotificationClickListener(listener: OnNotificationClickListener) {
        onNotificationClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        return NotificationsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                com.greenbay.app.R.layout.notification_item,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount() = notifications.size


    class NotificationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = NotificationItemBinding.bind(view)

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(notification: Communication) {
            binding.notificationTitleTv.text = notification.title
            binding.notificationDescriptionTv.text = notification.description
            binding.notificationTimeTv.text =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(notification.dateCreated)
        }
    }
}