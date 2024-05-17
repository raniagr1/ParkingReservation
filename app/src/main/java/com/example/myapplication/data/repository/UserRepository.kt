package com.example.myapplication.data.repository

import com.example.myapplication.Endpoint
import com.example.myapplication.data.model.DataClass
import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.User
import retrofit2.Response

class userRepository(private val endpoint: Endpoint) {
    suspend fun getAllparkings() = endpoint.getparkings()

    suspend fun registerUser(user: User): Response<Unit> {
        return endpoint.registerUser(user)
    }
    suspend fun userExists(loginRequest:LoginRequest ): Response<DataClass> {
//        val loginRequest = LoginRequest(userName, passwrd)
        return endpoint.userExist(loginRequest)
    }
    /* suspend fun getParkingById(parkingId: Int?): Parking? {
         return withContext(Dispatchers.IO) {
             parkingDao.getParkingById(parkingId)
         }
     }*/
}