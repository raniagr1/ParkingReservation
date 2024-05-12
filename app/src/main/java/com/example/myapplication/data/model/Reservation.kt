package com.example.myapplication.data.model
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reservations",
  )
data class Reservation(
    @PrimaryKey val reservationId: Int = 0,
    //numReservation is the value returned in the reservation from the server
    val placeId: Int,
    val userId: Int,
    val date: Date,
    @Ignore  val dateString:String,
    val entryTime: String,
    val exitTime: String,
    val paymentValidated: Boolean,


)
