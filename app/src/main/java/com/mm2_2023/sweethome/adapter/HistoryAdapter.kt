package com.mm2_2023.sweethome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mm2_2023.sweethome.R
import com.mm2_2023.sweethome.model.ModelDatabase
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HistoryAdapter(var modelDatabase: MutableList<ModelDatabase>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    // Fungsi untuk mengatur data adapter
    fun setDataAdapter(items: List<ModelDatabase>) {
        modelDatabase.clear()
        modelDatabase.addAll(items)
        notifyDataSetChanged()
    }

    // Fungsi untuk membuat tampilan (ViewHolder) untuk setiap item dalam RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_history, parent, false)
        return ViewHolder(view)
    }

    // Fungsi untuk menghubungkan data dari ModelDatabase ke ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelDatabase[position]

        // Format tanggal
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val checkInDate = dateFormat.parse(data.checkIn)
        val checkOutDate = dateFormat.parse(data.checkOut)

        // Menghitung selisih waktu antara check-in dan check-out
        val diffInMillis = checkOutDate.time - checkInDate.time
        val numberOfNights = TimeUnit.MILLISECONDS.toDays(diffInMillis)

        // Menetapkan hasil perhitungan ke tampilan TextView
        holder.tvTotalNight.text = "Number of nights: $numberOfNights"
    }

    // Kelas ViewHolder untuk menyimpan referensi tampilan item dalam RecyclerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTotalNight: TextView = itemView.findViewById(R.id.tvTotalNight)
    }

    // Fungsi untuk mendapatkan jumlah item dalam RecyclerView
    override fun getItemCount(): Int {
        return modelDatabase.size
    }

    // Fungsi untuk menghapus item saat dilakukan swipe
    fun setSwipeRemove(position: Int): ModelDatabase {
        val removedItem = modelDatabase.removeAt(position)
        notifyItemRemoved(position)
        return removedItem
    }

}
