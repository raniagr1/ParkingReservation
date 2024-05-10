package com.example.trying.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "places",
    foreignKeys = [ForeignKey(entity = Floor::class,
        parentColumns = ["id"],
        childColumns = ["floorId"],
        onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["floorId"])])
data class Place(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val number: Int,
    val floorId: Int // Foreign key referencing the floor
)
