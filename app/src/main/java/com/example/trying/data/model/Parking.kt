package com.example.trying.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "parkings")
data class Parking(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val commune: String,
    val imageResId: Int,
    val capacity: Int,
    val pricePerHour: Double,
    val description: String,
    val rating: Float,
    val longitude:Float,
    val latitude:Float
)