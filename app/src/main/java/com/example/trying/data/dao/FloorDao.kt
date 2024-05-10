package com.example.trying.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.trying.data.model.Floor

@Dao
interface FloorDao {
    @Query("SELECT * FROM floors WHERE parkingId = :parkingId")
    suspend fun getFloorsByParking(parkingId: Int): List<Floor>

    // Add more queries as needed
}
