package com.example.trying.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trying.data.dao.ReservationDao
import com.example.trying.data.model.Parking
import com.example.trying.data.model.Reservation
import com.example.trying.data.repository.ParkingRepository
import com.example.trying.data.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReservationsViewModel(private val reservationDao: ReservationDao) : ViewModel() {
    private val repository: ReservationRepository
    private val _allReservations = MutableStateFlow<List<Reservation>>(emptyList())
    val allReservations: StateFlow<List<Reservation>> = _allReservations
    init {
        repository = ReservationRepository(reservationDao)
        // Initialize allParkings in viewModelScope
        viewModelScope.launch {
            _allReservations.value = repository.getAllReservations()
        }
    }
    fun getUserReservations(userId: Int): List<Reservation> {
        return reservationDao.getUserReservations(userId)
    }

    suspend fun insert(reservation: Reservation) {
        reservationDao.insert(reservation)
    }
}
