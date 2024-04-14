package com.example.trying.data.repository

import com.example.trying.data.dao.ParkingDao
import com.example.trying.data.model.Parking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParkingRepository(private val parkingDao: ParkingDao) {
    suspend fun getAllParkings(): List<Parking> {
        return withContext(Dispatchers.IO) {
         parkingDao.getAllParkings()}
    }
    suspend fun getParkingById(parkingId: Int?): Parking? {
        return withContext(Dispatchers.IO) {
            parkingDao.getParkingById(parkingId)
        }
    }

    // Implement other repository methods as needed
}