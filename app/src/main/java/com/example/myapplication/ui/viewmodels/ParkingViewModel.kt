package com.example.myapplication.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Parking
import com.example.myapplication.data.repository.parkingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ParkingViewModel(private val parkingRepository: parkingRepository): ViewModel() {
    val data = mutableStateOf(listOf<Parking>())
    val loading = mutableStateOf(false)
    val displayMessage = mutableStateOf(false)

    fun getAllparkings(){
        loading.value=true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = parkingRepository.getAllparkings()
                loading.value = false
                if (response.isSuccessful) {
                    val parkings = response.body()
                    if (parkings != null) {
                        data.value = parkings
                    } else {
                        displayMessage.value = true
                    }
                }
            }
        }


    }
    /*

     init {
          repository = ParkingRepository(dao)
          // Initialize allParkings in viewModelScope
          viewModelScope.launch {
              _allParkings.value = repository.getAllParkings()
          }
      }

    suspend fun getParkingById(parkingId: Int?): Parking? {
          return repository.getParkingById(parkingId)
      }*/
    class Factory(private val parkingRepository: parkingRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ParkingViewModel(parkingRepository) as T
        }
    }
}