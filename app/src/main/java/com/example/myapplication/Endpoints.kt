package com.example.myapplication

import com.example.myapplication.URL
import com.example.myapplication.data.model.Parking
import com.example.myapplication.data.model.Reservation
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.Call
import java.util.Date


interface Endpoint {
    @GET("getparkings.php")
    suspend fun getparkings() : Response<List<Parking>>
    @GET("getparkingbyid.php")
    suspend fun getParkingById(@Query("parkingId") parkingId: Int): Response<Parking>
  /*  @GET("getparkingbyid.php")
    suspend fun getParkingById(@Query("parkingId") parkingId: Int): Response<Parking>
*/
    @POST("check_available_places.php")
    suspend fun checkAvailablePlaces(@Body requestData: CheckAvailablePlacesRequest): Response<CheckAvailablePlacesResponse>

    @POST("createreservation.php")
    suspend fun createReservation(@Body reservation: Reservation): Response<Reservation>
    @POST("insert_reservation.php")
    suspend fun insertReservation(@Body reservation: Reservation): Response<ReservationResponse>
    @POST("save-token.php")
    fun saveToken(@Body tokenInfo: TokenInfo): Call<Void>
    companion object {
        @Volatile
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(Endpoint::class.java)
                }
            }
            return endpoint!!

        }


    }
}


data class CheckAvailablePlacesRequest(
    val parkingId: Int,
    val date: String
)

data class CheckAvailablePlacesResponse(
    val reserved_places_total: Int
)

data class ReservationResponse(
    val status: String,
    val message: String,
    val reservationId: Int? = null,
    val place_num: Int? = null
)

data class TokenInfo(val token: String, val userId: Int)