package com.example.myapplication

import com.example.myapplication.URL
import com.example.myapplication.data.model.Parking
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface Endpoint {
    @GET("getparkings.php")
    suspend fun getparkings() : Response<List<Parking>>
    @GET("getparkingbyid.php")
    suspend fun getParkingById(@Query("parkingId") parkingId: Int): Response<Parking>

    companion object {
        @Volatile
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(Endpoint::class.java)
                }
            }
            return endpoint!!

        }


    }
}