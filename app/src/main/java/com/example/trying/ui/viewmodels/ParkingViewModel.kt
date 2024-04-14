package com.example.trying.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trying.data.dao.ParkingDao
import com.example.trying.data.model.Parking
import com.example.trying.data.repository.ParkingRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ParkingViewModel(
    private val dao: ParkingDao
): ViewModel() {
    private val repository: ParkingRepository

    // Declare allParkings as StateFlow
    private val _allParkings = MutableStateFlow<List<Parking>>(emptyList())
    val allParkings: StateFlow<List<Parking>> = _allParkings

    init {
        repository = ParkingRepository(dao)
        // Initialize allParkings in viewModelScope
        viewModelScope.launch {
            _allParkings.value = repository.getAllParkings()
        }
    }

    suspend fun getParkingById(parkingId: Int?): Parking? {
        return repository.getParkingById(parkingId)
    }



}



