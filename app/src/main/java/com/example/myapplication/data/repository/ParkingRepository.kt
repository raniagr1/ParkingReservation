package com.example.myapplication.data.repository

import com.example.myapplication.Endpoint
import com.example.myapplication.data.model.Parking
import retrofit2.Response

class parkingRepository(private val endpoint: Endpoint) {
    suspend fun getAllparkings() = endpoint.getparkings()
    suspend fun getParkingById(parkingId: Int): Response<Parking> {
        return endpoint.getParkingById(parkingId)
    }
   /* suspend fun getParkingById(parkingId: Int?): Parking? {
        return withContext(Dispatchers.IO) {
            parkingDao.getParkingById(parkingId)
        }
    }*/
}