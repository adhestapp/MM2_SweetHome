package com.mm2_2023.sweethome.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mm2_2023.sweethome.InputDataViewModel
import com.mm2_2023.sweethome.database.DatabaseClient
import com.mm2_2023.sweethome.database.DatabaseClient.Companion.getInstance
import com.mm2_2023.sweethome.database.dao.DatabaseDao
import com.mm2_2023.sweethome.model.ModelDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    var hotelList: List<ModelDatabase> = emptyList()

    val inputDataViewModel = InputDataViewModel(application)
    private var databaseDao: DatabaseDao? = DatabaseClient.getInstance(application).getDatabaseDao()

    fun deleteDataById(uid: Int) {
        Completable.fromAction {
            databaseDao?.deleteDataById(uid)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    init {
        databaseDao = getInstance(application)?.appDatabase?.databaseDao()
        hotelList = databaseDao?.getAllData() ?: emptyList()
    }

}