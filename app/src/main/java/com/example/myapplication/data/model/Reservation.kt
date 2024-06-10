package com.example.myapplication.data.model
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reservations")
data class Reservation(
    @PrimaryKey val reservationId: Int = 0,
    val parkId: Int,
    val userId: Int,
    val date: Date,
    val entryTime: String,
    val exitTime: String,
    val paymentValidated: Boolean,
    val placeNum: Int? = null,

) {
    @Ignore
    var dateString: String = ""
    @Ignore
    var imgUrl: String = ""
    @Ignore
    var parkName: String = ""
}

