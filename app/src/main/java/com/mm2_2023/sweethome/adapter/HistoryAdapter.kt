package com.mm2_2023.sweethome.adapter

import android.view.Display.Mode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mm2_2023.sweethome.R
import com.mm2_2023.sweethome.model.ModelDatabase
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.list_item_history.view.*


class HistoryAdapter(var modelDatabase: MutableList<ModelDatabase>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    fun setDataAdapter(items: List<ModelDatabase>){
        modelDatabase.clear()
        modelDatabase.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_history, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelDatabase[position]

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val checkInDate = dateFormat.parse(data.checkIn)
        val checkOutDate = dateFormat.parse(data.checkOut)

        val diffInMillis = checkOutDate.time - checkInDate.time
        val numberOfNights = TimeUnit.MILLISECONDS.toDays(diffInMillis)

        // Now, you can use the numberOfNights as needed
        holder.tvTotalNight.text = "Number of nights: $numberOfNights"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTotalNight: TextView = itemView.findViewById(R.id.tvTotalNight)
    }
    }
