package com.example.trying.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.trying.data.model.Place

@Dao
interface PlaceDao {
    @Query("SELECT * FROM places WHERE floorId = :floorId")
    suspend fun getPlacesByFloor(floorId: Int): List<Place>

    // Add more queries as needed
}
