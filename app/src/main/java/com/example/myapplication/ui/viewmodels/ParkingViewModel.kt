package com.example.myapplication.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val _parking = mutableStateOf<Parking?>(null)
   // val _parking = MutableLiveData<Parking>()
   // val parking: LiveData<Parking> = _parking
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
    fun getParkingById(parkingId: Int) {
        loading.value=true
        viewModelScope.launch {
            val response = parkingRepository.getParkingById(parkingId)
            loading.value = false
            if (response.isSuccessful) {
                _parking.value = response.body()
            } else {
                // Handle error
                Log.e("ParkingViewModel", "Failed to get parking: ${response.message()}")
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