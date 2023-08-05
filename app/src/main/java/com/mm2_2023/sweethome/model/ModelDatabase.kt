package com.mm2_2023.sweethome.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_hotel")
class ModelDatabase(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0, // Kunci utama untuk data di tabel, di-generate secara otomatis saat data ditambahkan ke database

    var namaTamu: String = "", // Nama tamu yang memesan hotel

    var checkIn: String = "", // Tanggal check-in tamu

    var checkOut: String = "", // Tanggal check-out tamu

    var nomorTelepon: String = "", // Nomor telepon tamu

    var anakAnak: Int = 0, // Jumlah anak-anak yang ikut menginap

    var dewasa: Int = 0, // Jumlah dewasa yang ikut menginap

    var hargaHotel: Int = 0, // Harga kamar hotel per malam

    var kelas: String = "", // Kelas kamar hotel (contoh: Standard, Deluxe, Suite, dsb)

    var status: String = "", // Status pesanan (contoh: Booked, Check-in, Check-out, Cancelled, dsb)

    var description: String = "", // Deskripsi hotel (contoh: fasilitas, layanan, dsb)

    var location: String = "", // Lokasi hotel (contoh: alamat, kota, negara, dsb)

    var name: String = "" // Nama hotel
)
