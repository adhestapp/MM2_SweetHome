import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mm2_2023.sweethome.InputDataViewModel
import com.mm2_2023.sweethome.R
import com.mm2_2023.sweethome.adapter.RecyclerViewAdapter
import com.mm2_2023.sweethome.database.AppDatabase
import com.mm2_2023.sweethome.model.ModelDatabase
import com.mm2_2023.sweethome.view.HistoryActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    // Deklarasi variabel
    private lateinit var imageList: ArrayList<Int>
    private var hotelList: List<ModelDatabase> = emptyList()
    private var recyclerView: RecyclerView? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Memulai inisialisasi data untuk daftar gambar
        initializeData()

        // Menyiapkan RecyclerView dan memuat data dari database
        setupRecyclerView()

        // Mencari NavController yang terkait dengan BottomNavigationView
        navController = findNavController(R.id.bottomNav)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav)

        // Menyambungkan BottomNavigationView dengan NavController untuk navigasi
        bottomNav.setupWithNavController(navController)

        // Menetapkan pendengar klik item untuk BottomNavigationView
        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    // Mengatasi klik item Home (berpindah ke HomeActivity)
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.bottom_book -> {
                    // Mengatasi klik item Dashboard (berpindah ke HistoryActivity)
                    startActivity(Intent(this, HistoryActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    // Fungsi untuk menginisialisasi imageList dengan beberapa data
    private fun initializeData() {
        imageList = ArrayList()
        imageList.add(R.drawable.banner1)
        imageList.add(R.drawable.banner2)
        imageList.add(R.drawable.banner3)
    }

    // Fungsi untuk menyiapkan RecyclerView dan memuat data dari database
    private fun setupRecyclerView() {
        // Panggil loadModelDatabaseFromDatabase() untuk mengambil data hotel dari database
        loadModelDatabaseFromDatabase()

        recyclerView = findViewById(R.id.recyclerView)
        val recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView?.adapter = recyclerViewAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this)

        // Buat instance dari InputDataViewModel untuk berinteraksi dengan database
        val inputDataViewModel = InputDataViewModel(application) // Gunakan "this.application" jika Anda berada dalam sebuah Activity

        // Panggil fungsi addDataPemesan dengan parameter yang diperlukan untuk memasukkan data ke dalam database
        inputDataViewModel.addDataPemesan(
            nama_tamu = "John Doe",
            check_in = "2023-08-10",
            check_out = "2023-08-15",
            nomor_telepon = "123456789",
            anak_anak = 2,
            dewasa = 2,
            harga_hotel = 500,
            kelas = "Deluxe",
            status = "Booked",
            name = "Hotel Santika",
            description = "Hotel ini bagus",
            location = "Semarang"
        )
    }

    // Objek migrasi untuk menangani perubahan skema database
    val migration1To2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Tambahkan kode migrasi di sini jika ada perubahan skema
        }
    }

    // Fungsi untuk mengambil data dari database menggunakan Room database dan memperbarui RecyclerView
    private fun loadModelDatabaseFromDatabase() {
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).addMigrations(migration1To2) // Tambahkan migrasi di sini
            .fallbackToDestructiveMigration()
            .build()

        lifecycleScope.launch(Dispatchers.IO) {
            hotelList = database.databaseDao()!!.getAllData()

            withContext(Dispatchers.Main) {
                val recyclerViewAdapter = recyclerView?.adapter as RecyclerViewAdapter
                recyclerViewAdapter.updateData(hotelList)

            }
        }
    }
}
