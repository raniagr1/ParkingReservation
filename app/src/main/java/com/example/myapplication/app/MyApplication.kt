package com.example.myapplication.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.common.Endpoint
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.repository.ReservationRepository
import com.example.myapplication.data.repository.TokenRepository
import com.example.myapplication.data.repository.parkingRepository
import com.example.myapplication.data.repository.userRepository

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name ="LocalStore")

class MyApplication:Application() {
    private val dataBase by lazy { AppDatabase.getInstance(this) }

    val endpoint by lazy { Endpoint.createEndpoint() }
    val parkingRepository by lazy { parkingRepository(endpoint) }
    val tokenRepository by lazy { TokenRepository(endpoint) }
    private val reservationDao by lazy { dataBase.getReservationDao() }

    val reservationRepository by lazy  { ReservationRepository(endpoint,reservationDao) }

    val userRepository by lazy { userRepository(endpoint) }
}