package com.greenbay.app.ui.home.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greenbay.app.ui.home.models.Communication

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
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = notifications.size


    class NotificationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}