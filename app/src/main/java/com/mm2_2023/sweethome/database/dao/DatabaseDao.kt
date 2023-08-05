package com.mm2_2023.sweethome.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mm2_2023.sweethome.model.ModelDatabase

// Definisi antarmuka Dao menggunakan anotasi @Dao
@Dao
interface DatabaseDao {

    // Fungsi untuk mendapatkan semua data dari tabel tbl_hotel
    @Query("SELECT * FROM tbl_hotel")
    fun getAllData(): List<ModelDatabase>

    // Fungsi untuk menyisipkan data ke dalam tabel tbl_hotel
    // Jika ada konflik pada primary key (uid), data akan digantikan (onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(vararg modelDatabase: ModelDatabase)

    // Fungsi untuk menghapus data dari tabel tbl_hotel berdasarkan uid
    @Query("DELETE FROM tbl_hotel WHERE uid = :uid")
    fun deleteDataById(uid: Int)
}
