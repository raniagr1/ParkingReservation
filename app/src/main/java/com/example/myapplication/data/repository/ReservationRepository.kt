package com.example.myapplication.data.repository
/*
import com.example.myapplication.data.dao.ParkingDao
import com.example.myapplication.data.dao.ReservationDao
import com.example.myapplication.data.model.Reservation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReservationRepository (private val ReservationDao: ReservationDao){
    suspend fun getAllReservations(): List<Reservation> {
        return withContext(Dispatchers.IO) {
            ReservationDao.getAllReservations()}
    }
    suspend fun getReservationById(userId: Int): List<Reservation>? {
        return withContext(Dispatchers.IO) {
            ReservationDao.getUserReservations(userId)
        }
    }

}

 */