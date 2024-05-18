package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.repository.ReservationRepository
import com.example.myapplication.data.repository.parkingRepository

class MyApplication:Application() {
    private val dataBase by lazy { AppDatabase.getInstance(this) }

    val endpoint by lazy { Endpoint.createEndpoint() }
    val parkingRepository by lazy { parkingRepository(endpoint) }
    private val reservationDao by lazy { dataBase.getReservationDao() }

    val reservationRepository by lazy  { ReservationRepository(endpoint,reservationDao) }
}