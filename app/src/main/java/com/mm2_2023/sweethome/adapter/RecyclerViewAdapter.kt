package com.mm2_2023.sweethome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mm2_2023.sweethome.R
import com.mm2_2023.sweethome.model.Hotel

class RecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var hotelList: List<Hotel> = emptyList()

    // Method to update the RecyclerView data
    fun updateData(newHotelList: List<Hotel>) {
        hotelList = newHotelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.design_dashboard_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel = hotelList[position]

        holder.hotelName.text = hotel.name
        holder.hotelPrice.text = hotel.price.toString()

        // Set the hotel image using Glide or any other image loading library
        // For example, if you are using Glide:
        // Glide.with(context).load(hotel.imageUrl).into(holder.hotelImage)
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hotelName: TextView = itemView.findViewById(R.id.name_hotel)
        val hotelPrice: TextView = itemView.findViewById(R.id.priceHotel)
        val hotelImage: ImageView = itemView.findViewById(R.id.imageHotel)
    }
}
