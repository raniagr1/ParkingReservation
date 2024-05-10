package com.example.myapplication.data.model
/*
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "floors",
    foreignKeys = [ForeignKey(entity = Parking::class,
        parentColumns = ["id"],
        childColumns = ["parkingId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["parkingId"])])
data class Floor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val number: Int,
    val parkingId: Int,
    val nbPlaces:Int

)
*/