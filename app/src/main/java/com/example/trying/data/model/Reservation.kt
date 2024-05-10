package com.example.trying.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reservations",
    foreignKeys = [ForeignKey(entity = Place::class,
        parentColumns = ["id"],
        childColumns = ["placeId"],
        onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE)],)
data class Reservation(
    @PrimaryKey(autoGenerate = true) val reservationId: Int = 0,
    val placeId: Int,
    val userId: Int,
    val date: Date,
    val entryTime: String,
    val exitTime: String,
    val paymentValidated: Boolean,


)
