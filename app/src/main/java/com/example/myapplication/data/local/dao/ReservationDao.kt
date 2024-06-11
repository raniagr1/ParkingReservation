package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.Reservation

//import com.example.myapplication.data.model.Reservation



@Dao
interface ReservationDao {
    @Insert
    suspend fun insertReservation(reservation: Reservation)

    @Query("SELECT * FROM reservations")
    fun getAllReservations(): List<Reservation>

    @Query("SELECT * FROM reservations WHERE userId = :userId")
    fun getUserReservations(userId: Int): List<Reservation>

    @Query("SELECT * FROM reservations WHERE reservationId = :resId")
    fun getReservationById(resId: Int): Reservation

}