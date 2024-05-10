package com.example.trying.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trying.data.model.Reservation
import kotlinx.coroutines.flow.Flow


    @Dao
    interface ReservationDao {
        @Insert
        suspend fun insert(reservation: Reservation)

        @Query("SELECT * FROM reservations")
        fun getAllReservations(): List<Reservation>

        @Query("SELECT * FROM reservations WHERE userId = :userId")
        fun getUserReservations(userId: Int): List<Reservation>

        @Query("SELECT * FROM reservations WHERE entryTime >= :date")
        fun getReservationsFromDate(date: Long): List<Reservation>
    }


