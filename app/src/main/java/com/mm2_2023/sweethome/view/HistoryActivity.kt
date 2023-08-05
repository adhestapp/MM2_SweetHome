package com.mm2_2023.sweethome.view

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mm2_2023.sweethome.R
import com.mm2_2023.sweethome.adapter.HistoryAdapter
import com.mm2_2023.sweethome.databinding.ActivityHistoryBinding
import com.mm2_2023.sweethome.model.ModelDatabase
import com.mm2_2023.sweethome.viewmodel.HistoryViewModel


class HistoryActivity : AppCompatActivity() {
    // Deklarasi variabel
    var hotelList: MutableList<ModelDatabase> = mutableListOf() // Ubah ke mutableListOf()
    lateinit var historyAdapter: HistoryAdapter
    lateinit var historyViewModel: HistoryViewModel
    lateinit var rvHistory: RecyclerView // Deklarasikan variabelnya
    lateinit var tvNotFound: TextView // Deklarasikan variabelnya
    lateinit var toolbar: Toolbar // Deklarasikan variabelnya

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setStatusBar()
        setToolbar()
        setInitLayout()
        setViewModel()
        setUpItemTouchHelper()
    }

    // Mengatur Toolbar sebagai ActionBar
    private fun setToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    // Mengatur tampilan awal untuk RecyclerView dan Adapter
    private fun setInitLayout() {
        historyAdapter = HistoryAdapter(hotelList)
        rvHistory = findViewById(R.id.rvHistory) // Tambahkan baris ini untuk menginisialisasi rvHistory
        rvHistory.setHasFixedSize(true)
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = historyAdapter
    }

    // Mendapatkan ViewModel dan inisialisasi data dari ViewModel
    private fun setViewModel() {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        hotelList =
            historyViewModel.hotelList as MutableList<ModelDatabase> // Ambil hotelList dari ViewModel
        historyAdapter.setDataAdapter(hotelList)
    }

    // Mengatur ItemTouchHelper untuk menangani penggeseran item di RecyclerView
    private fun setUpItemTouchHelper() {
        val simpleCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedPosition = viewHolder.adapterPosition
                val modelDatabase = historyAdapter.setSwipeRemove(swipedPosition)
                val uid = modelDatabase.uid
                historyViewModel.deleteDataById(uid)
                Toast.makeText(
                    this@HistoryActivity,
                    "Data yang dipilih sudah dihapus",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rvHistory)
    }

    // Mengatur status bar dengan warna transparan dan teks status bar berwarna hitam (hanya untuk API >= 23)
    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    // Mengatur aksi ketika tombol kembali di ActionBar ditekan
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        // Fungsi untuk mengatur status flag di window
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }
}