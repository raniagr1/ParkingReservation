package com.example.myapplication.data.repository

import com.example.myapplication.Endpoint

class parkingRepository(private val endpoint: Endpoint) {
    suspend fun getAllparkings() = endpoint.getparkings()
   /* suspend fun getParkingById(parkingId: Int?): Parking? {
        return withContext(Dispatchers.IO) {
            parkingDao.getParkingById(parkingId)
        }
    }*/
}