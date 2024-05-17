package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.ViewModel

import com.example.myapplication.data.model.User

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.DataClass
import com.example.myapplication.data.model.Parking
import com.example.myapplication.data.repository.parkingRepository
import com.example.myapplication.data.repository.userRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserViewModel(private val userRepository: userRepository): ViewModel() {
    val data = mutableStateOf(listOf<Parking>())
    val loading = mutableStateOf(false)
    val displayMessage = mutableStateOf(false)
    val _user = mutableStateOf<User?>(null)
    val userExistsState: MutableState<Boolean> = mutableStateOf(false)
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean>
        get() = _isLoggedIn
    var loginResult = mutableStateOf<DataClass?>(null)
        private set

    fun checkUserExistence(userName: String, passwrd: String) {
        viewModelScope.launch {
            val response = userRepository.userExist(userName, passwrd)
            loginResult.value = response.body()
        }
    }
    // val _parking = MutableLiveData<Parking>()
    // val parking: LiveData<Parking> = _parking
    /*fun getAllparkings(){
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


    }*/

//    fun checkUserExistence(userName: String, passwrd: String) {
//        viewModelScope.launch {
//            val response = userRepository.userExist(userName, passwrd)
//            if (response.isSuccessful && response.body() != null) {
//                val loginResponse = response.body()
//                if (loginResponse?.success == true) {
//                    // Login successful
//                } else {
//                    // Login failed, show message
//                }
//            } else {
//                // Handle error
//            }
//        }
//    }
    // Function to handle login


//    fun userExists(user: User) {
//        viewModelScope.launch(Dispatchers.IO) {
//            // Set loading state to true while fetching data
//            userExistsState.value = true
//
//            val response = userRepository.userExist(user)
//            val success = response.isSuccessful && response.body()?.success == true
//
//            // Update loading state and notify observers
//            userExistsState.value = false
//            userExistsState.value = success
//        }
//    }

//    fun userExists(user: User): MutableState<Boolean> {
//        val userisExists = mutableStateOf(false)
//        viewModelScope.launch {
//                val existingUser = userRepository.userExist(user)
//                val success = existingUser.body()?.success ?: false
//                userisExists.value = success
//        }
//        return userisExists
//    }

//    fun signIn(username: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
//        viewModelScope.launch {
//            try {
//                val userExists = userExist(username, password)
//                if (userExists) {
//                    _isLoggedIn.value = true
//                    onSuccess()
//                } else {
//                    onError()
//                }
//            } catch (e: Exception) {
//                // Handle exceptions here
//                onError()
//            }
//        }
//    }

    suspend fun registerUser(user: User): Boolean {
        loading.value = true
        return withContext(Dispatchers.IO) {
            val response = userRepository.registerUser(user)
            loading.value = false
            return@withContext response.isSuccessful
        }
    }

    fun loggedin() {
        _isLoggedIn.value = true
    }
    fun logout() {
        _isLoggedIn.value = false
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
    class Factory(private val userRepository: userRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel(userRepository) as T
        }
    }
}