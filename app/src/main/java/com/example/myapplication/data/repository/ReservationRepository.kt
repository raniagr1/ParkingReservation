package com.example.myapplication.data.repository

import com.example.myapplication.CheckAvailablePlacesRequest
import com.example.myapplication.CheckAvailablePlacesResponse
import com.example.myapplication.Endpoint
import com.example.myapplication.data.model.Parking
import com.example.myapplication.data.model.Reservation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ReservationRepository (private val endpoint: Endpoint) {
    suspend fun createReservation(reservation: Reservation): Response<Reservation> {
        return endpoint.createReservation(reservation)
    }

    suspend fun checkAvailablePlaces(request: CheckAvailablePlacesRequest): Response<CheckAvailablePlacesResponse> {
        return endpoint.checkAvailablePlaces(request)
    }
  /*  suspend fun getAllReservations(): List<Reservation> {
        return withContext(Dispatchers.IO) {
            ReservationDao.getAllReservations()}
    }
    suspend fun getReservationById(userId: Int): List<Reservation>? {
        return withContext(Dispatchers.IO) {
            ReservationDao.getUserReservations(userId)
        }
    }**/

}

