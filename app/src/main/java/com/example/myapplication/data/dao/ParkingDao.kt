package com.example.myapplication.data.dao
/*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.model.Parking


@Dao
interface ParkingDao {
    @Query("SELECT * FROM parkings")
    fun getAllParkings(): List<Parking>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(parking: Parking)
    // Add more queries as needed
    @Insert
    fun insertAll(parkings: List<Parking>)

    @Query("SELECT * FROM parkings WHERE id = :parkingId")
    suspend fun getParkingById(parkingId: Int?): Parking
}
        */