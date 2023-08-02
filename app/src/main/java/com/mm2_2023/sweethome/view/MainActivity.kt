package com.mm2_2023.sweethome.view

import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mm2_2023.sweethome.adapter.ImageAdapter
import com.mm2_2023.sweethome.R
import com.mm2_2023.sweethome.adapter.RecyclerViewAdapter
import com.mm2_2023.sweethome.database.HotelApiService
import com.mm2_2023.sweethome.model.Hotel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: android.os.Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var imageAdapter: ImageAdapter

    private var hotelList: List<Hotel> = ArrayList()

    private var recyclerView: RecyclerView? = null
    private val al_image_hotel = ArrayList<Int>()
    private val al_name_hotel = ArrayList<String>()
    private val al_desc_hotel = ArrayList<String>()
    private val al_price_hotel = ArrayList<Int>()
    private val al_location = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeData()
        setupViewPager()
        setupRecyclerView()

        // Create a Handler instance associated with the main thread's looper
        handler = android.os.Handler(Looper.getMainLooper())

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })
    }

    private fun initializeData() {
        imageList = ArrayList()
        imageList.add(R.drawable.banner1)
        imageList.add(R.drawable.banner2)
        imageList.add(R.drawable.banner3)
    }

    private fun setupViewPager() {
        viewPager2 = findViewById(R.id.viewPager2)
        handler = android.os.Handler(Looper.myLooper()!!)
        imageAdapter = ImageAdapter(imageList)
        viewPager2.adapter = imageAdapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        imageAdapter = ImageAdapter(imageList, viewPager2)
    }
/*
    private fun loadHotelData() {
        al_image_hotel.add(R.drawable.banner1)
        al_image_hotel.add(R.drawable.img2)
        al_image_hotel.add(R.drawable.img1)

        al_name_hotel.add("Hotel A")
        al_name_hotel.add("Hotel B")
        al_name_hotel.add("Hotel C")

        al_desc_hotel.add("Description of Hotel A")
        al_desc_hotel.add("Description of Hotel B")
        al_desc_hotel.add("Description of Hotel C")

        al_price_hotel.add(100)
        al_price_hotel.add(150)
        al_price_hotel.add(200)

        al_location.add("City X")
        al_location.add("City Y")
        al_location.add("City Z")
    }*/

    private fun setupRecyclerView() {
        // Call loadHotelDataFromAPI() to fetch hotel data from the API
        loadHotelDataFromAPI()

        recyclerView = findViewById(R.id.recyclerView)
        val recyclerViewAdapter = RecyclerViewAdapter(emptyList(), this)
        recyclerView?.adapter = recyclerViewAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)
    }

    private fun loadHotelDataFromAPI() {
        // Create a Retrofit instance with the base URL of your API
        val retrofit = Retrofit.Builder()
            .baseUrl("https://travel-advisor.p.rapidapi.com/locations/v2/auto-complete?query=eiffel%20tower&lang=en_US&units=km") // Replace with your actual API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create the API service using the Retrofit instance
        val apiService = retrofit.create(HotelApiService::class.java)

        // Make the API call to fetch the list of hotels
        val call: Call<List<Hotel>> = apiService.getHotels()

        call.enqueue(object : Callback<List<Hotel>> {
            override fun onResponse(call: Call<List<Hotel>>, response: Response<List<Hotel>>) {
                if (response.isSuccessful) {
                    // The API call was successful, get the list of hotels from the response
                    hotelList = response.body() ?: emptyList()

                    // Update the RecyclerView adapter with the fetched data
                    val recyclerViewAdapter = recyclerView.adapter as RecyclerViewAdapter
                    recyclerViewAdapter.updateData(hotelList)
                } else {
                    // Handle error response
                }
            }

            override fun onFailure(call: Call<List<Hotel>>, t: Throwable) {
                // Handle network failure
            }
        })
    }


}
