package com.mm2_2023.sweethome.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mm2_2023.sweethome.model.ModelDatabase

@Dao
interface DatabaseDao {
    @Query("SELECT * FROM tbl_hotel")
    fun getAllData() : LiveData<List<ModelDatabase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(vararg modelDatabase: ModelDatabase)

    @Query("DELETE FROM tbl_hotel WHERE uid= :uid")
    fun deleteDataById(uid: Int)
}