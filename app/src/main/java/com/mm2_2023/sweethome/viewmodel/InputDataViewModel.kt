package com.mm2_2023.sweethome

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mm2_2023.sweethome.database.DatabaseClient.Companion.getInstance
import com.mm2_2023.sweethome.database.dao.DatabaseDao
import com.mm2_2023.sweethome.model.ModelDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler

class InputDataViewModel(application: Application) : AndroidViewModel(application)  {
    var databaseDao : DatabaseDao?

    fun addDataPemesan(
        nama_tamu: String, check_in: String, check_out: String,
        nomor_telepon: String, anak_anak: Int, dewasa: Int,
        harga_hotel: Int, kelas:String, status: String){

        Completable.fromAction{
            val modelDatabase = ModelDatabase()
            modelDatabase.namaTamu = nama_tamu
            modelDatabase.checkIn = check_in
            modelDatabase.checkOut = check_out
            modelDatabase.nomorTelepon = nomor_telepon
            modelDatabase.anakAnak = anak_anak
            modelDatabase.dewasa = dewasa
            modelDatabase.hargaHotel = harga_hotel
            modelDatabase.kelas = kelas
            modelDatabase.status = status
            databaseDao?.insertData(modelDatabase)
        }
            .subscribeOn(Scheduler.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
    init {
        databaseDao = getInstance(application).appDatabase.databaseDao()
    }
}