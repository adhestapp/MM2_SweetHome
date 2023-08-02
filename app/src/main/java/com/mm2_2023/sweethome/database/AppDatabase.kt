package com.mm2_2023.sweethome.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mm2_2023.sweethome.database.dao.DatabaseDao
import com.mm2_2023.sweethome.model.ModelDatabase

@Database(entities = [ModelDatabase::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao() : DatabaseDao?
}