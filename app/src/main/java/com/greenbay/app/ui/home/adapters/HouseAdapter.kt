package com.greenbay.app.ui.home.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.greenbay.app.R
import com.greenbay.app.databinding.HouseItemBinding
import com.greenbay.app.ui.home.models.House
import java.text.SimpleDateFormat
import java.util.Locale

class HouseAdapter(private var houses: List<House>) :
    RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {
    private lateinit var onHouseClickListener: OnHouseClickListener

    interface OnHouseClickListener {
        fun onHouseClick(position: Int)
    }

    fun setHouses(houses: List<House>) {
        this.houses = houses
        notifyDataSetChanged()
    }

    fun setOnHouseClickListener(listener: OnHouseClickListener) {
        onHouseClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        return HouseViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.house_item,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        holder.bind(houses[position])
    }

    override fun getItemCount() = houses.size

    class HouseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = HouseItemBinding.bind(view)

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(house: House) {
            binding.houseNumberTv.text = house.houseNumber
            binding.houseItemRentTv.text = house.rent
            binding.houseItemDepositTv.text = house.deposit
            binding.houseItemFloorTv.text = house.floorNumber
            if (house.occupied == true) {
                binding.houseTimeTv.text = "Occupied"
                binding.houseDot.setBackgroundColor(binding.root.context.getColor(R.color.red))
            } else {
                binding.houseTimeTv.text = "Vacant"
                binding.houseDot.setBackgroundColor(binding.root.context.getColor(R.color.green))
            }
        }
    }
}