package com.mm2_2023.sweethome.database

import android.content.Context
import androidx.room.Room
import com.mm2_2023.sweethome.database.dao.DatabaseDao

class DatabaseClient private constructor(private val context: Context) {

    // Objek AppDatabase digunakan untuk mengakses database
    val appDatabase: AppDatabase

    init {
        // Membangun database menggunakan Room.databaseBuilder()
        appDatabase = Room.databaseBuilder(
            context.applicationContext, // Gunakan applicationContext untuk menghindari memory leaks
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    // Fungsi ini digunakan untuk mendapatkan DatabaseDao yang digunakan untuk mengakses data dalam database
    fun getDatabaseDao(): DatabaseDao? {
        return appDatabase.databaseDao()
    }

    companion object {
        private lateinit var mInstance: DatabaseClient

        // Fungsi ini digunakan untuk mendapatkan instance DatabaseClient
        @Synchronized
        fun getInstance(context: Context): DatabaseClient {
            if (!::mInstance.isInitialized) {
                mInstance = DatabaseClient(context)
            }
            return mInstance
        }
    }
}
