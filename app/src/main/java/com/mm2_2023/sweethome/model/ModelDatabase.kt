package com.mm2_2023.sweethome.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "tbl_hotel")
class ModelDatabase : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    var uid = 0

    @ColumnInfo(name = "nama_tamu")
    lateinit var namaTamu: String

    @ColumnInfo(name = "check_in")
    lateinit var checkIn: String

    @ColumnInfo(name = "check_out")
    lateinit var checkOut: String

    @ColumnInfo(name = "harga_hotel")
    var hargaHotel = 0

    @ColumnInfo(name = "anak_anak")
    var anakAnak = 0

    @ColumnInfo(name = "dewasa")
    var dewasa = 0

    @ColumnInfo(name = "nomor_telepon")
    lateinit var nomorTelepon: String

    @ColumnInfo(name = "kelas")
    lateinit var kelas: String

    @ColumnInfo(name = "status")
    lateinit var status: String

}
