package com.example.myapplication.ui.viewmodels

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.CheckAvailablePlacesRequest
import com.example.myapplication.CheckAvailablePlacesResponse
import com.example.myapplication.data.model.Parking
import com.example.myapplication.data.model.Reservation

import com.example.myapplication.data.repository.ReservationRepository
import com.example.myapplication.data.repository.parkingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat

class ReservationsViewModel(private val repository: ReservationRepository) : ViewModel() {
    private val _reservationCreated = MutableLiveData<Boolean>()
    val reservationCreated: LiveData<Boolean> = _reservationCreated
    val availablePlaces = mutableStateOf<CheckAvailablePlacesResponse?>(null)
    val availablePlacesState=   mutableStateOf(0)
    val reservationStatus= mutableStateOf("")
    var reservationMessage=  mutableStateOf("")
    private val _allReservations = MutableStateFlow<List<Reservation>>(emptyList())
    val allReservations: StateFlow<List<Reservation>> = _allReservations
    //var availablePlaces=0;
    val displayMessage = mutableStateOf(false)

    init {
        // Initialize allReservations in viewModelScope
        viewModelScope.launch {
            _allReservations.value = repository.getAllReservations()
        }
    }
    fun createReservation(reservation: Reservation) {
        viewModelScope.launch {
            val response = repository.createReservation(reservation)
            _reservationCreated.value = response.isSuccessful
        }
    }

    fun insertReservation(request: Reservation) {
        viewModelScope.launch {
            val response = repository.insertReservation(request)
            if (response.isSuccessful) {
                reservationStatus.value = response.body()?.status!!
                reservationMessage.value = response.body()?.message!!
                if (response.body()?.reservationId != null) {
                    val reservation = Reservation(
                        reservationId = response.body()?.reservationId!!,
                        parkId = request.parkId,
                        userId = request.userId,
                        date = request.date,
                        entryTime = request.entryTime,
                        exitTime = request.exitTime,
                        paymentValidated = true // Payment is already validated
                    ).apply {
                        dateString = request.dateString
                    }
                    repository.addReservation(reservation)

                }
            } else {
                reservationStatus.value = "error"
                reservationMessage.value = "Failed to create reservation."
            }
            _allReservations.value = repository.getAllReservations()

        }
    }
     fun checkAvailablePlaces(request: CheckAvailablePlacesRequest){
         viewModelScope.launch {
        val response = repository.checkAvailablePlaces(request)

      //  loading.value = false
        if (response.isSuccessful) {

            val available = response.body()
            if (available != null) {
                availablePlaces.value = available
                availablePlacesState.value = available.reserved_places_total
            } else {
                displayMessage.value = true
            }
        }}
       // return repository
    }
    suspend fun getReservationsById(resId: Int): Reservation? {
        return repository.getReservationById(resId)
    }
  /*  fun checkAvailablePlaces(parkingId: Int , date: String):Int {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val parsedDate: java.util.Date? = dateFormatter.parse(date)

        viewModelScope.launch {
            val request = CheckAvailablePlacesRequest(
                parkingId = parkingId,
                date = parsedDate.toString()
            )
            val response = repository.checkAvailablePlaces(request)

            availablePlaces = response.
        }

        return availablePlaces;
    }*/
 /*   private val repository: ReservationRepository
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
    }*/
 class Factory(private val reservationRepository: ReservationRepository) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
         return ReservationsViewModel(reservationRepository) as T
     }
 }
}
