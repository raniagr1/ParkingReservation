package com.example.trying.data.repository

import com.example.trying.data.dao.ParkingDao
import com.example.trying.data.dao.ReservationDao
import com.example.trying.data.model.Reservation
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