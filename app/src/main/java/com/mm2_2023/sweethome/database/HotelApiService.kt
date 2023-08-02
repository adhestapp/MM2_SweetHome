package com.mm2_2023.sweethome.database


import com.mm2_2023.sweethome.model.Hotel
import retrofit2.Call
import retrofit2.http.GET

interface HotelApiService {
    @GET("hotels")
    fun getHotels(): Call<List<Hotel>>
}