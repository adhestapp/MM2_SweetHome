package com.mm2_2023.sweethome.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mm2_2023.sweethome.database.dao.DatabaseDao
import com.mm2_2023.sweethome.model.ModelDatabase

// Deklarasi anotasi @Database untuk menentukan entitas dan versi database
@Database(entities = [ModelDatabase::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Fungsi abstrak untuk mendapatkan objek DatabaseDao
    abstract fun databaseDao(): DatabaseDao?

    // Objek konstan yang menampung nama database dan versi database
    companion object {
        const val DATABASE_NAME = "HotelDatabase"
        const val DATABASE_VERSION = 2

        // Fungsi untuk membangun database menggunakan Room.databaseBuilder()
        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration() // Menggunakan strategi migrasi jika ada perubahan skema yang tidak kompatibel
                .build()
        }
    }
}
