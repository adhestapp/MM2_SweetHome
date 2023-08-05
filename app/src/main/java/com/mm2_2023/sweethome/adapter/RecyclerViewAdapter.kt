package com.mm2_2023.sweethome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mm2_2023.sweethome.R
import com.mm2_2023.sweethome.model.ModelDatabase

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()  {

    private var data: List<ModelDatabase> = emptyList()

    // Fungsi untuk membuat tampilan (ViewHolder) untuk setiap item dalam RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_history, parent, false)
        return ViewHolder(view)
    }

    // Fungsi untuk menghubungkan data dari ModelDatabase ke ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel = data[position]
        holder.bind(hotel)
    }

    // Fungsi untuk mendapatkan jumlah item dalam RecyclerView
    override fun getItemCount(): Int {
        return data.size
    }

    // Fungsi untuk mengupdate data pada RecyclerView
    fun updateData(newData: List<ModelDatabase>) {
        data = newData
        notifyDataSetChanged()
    }

    // Kelas ViewHolder untuk menyimpan referensi tampilan item dalam RecyclerView
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.name_hotel)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceHotel)

        // Fungsi untuk mengikat data dari ModelDatabase ke tampilan ViewHolder
        fun bind(modelDatabase: ModelDatabase) {
            nameTextView.text = modelDatabase.name
            priceTextView.text = modelDatabase.hargaHotel.toString()
        }
    }
}
