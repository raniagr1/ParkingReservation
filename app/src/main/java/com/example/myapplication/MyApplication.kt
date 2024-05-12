package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.repository.ReservationRepository
import com.example.myapplication.data.repository.parkingRepository

class MyApplication:Application() {
    val endpoint by lazy { Endpoint.createEndpoint() }
    val parkingRepository by lazy { parkingRepository(endpoint) }
    val reservationRepository by lazy  { ReservationRepository(endpoint) }
}