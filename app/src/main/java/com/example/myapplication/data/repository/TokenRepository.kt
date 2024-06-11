package com.example.myapplication.data.repository



import android.util.Log
import com.example.myapplication.common.Endpoint
import com.example.myapplication.common.TokenInfo
import com.example.myapplication.common.URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenRepository(private val endpoint: Endpoint) {

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL) // Replace with your server's base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(Endpoint::class.java)

    fun sendTokenToServer(token: String, userId: Int) {
        val tokenInfo = TokenInfo(token, userId)

        service.saveToken(tokenInfo).enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                Log.d("TokenRepository", "Token sent successfully: ${response.message()}")
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                Log.e("TokenRepository", "Error sending token: ${t.message}")
            }
        })
    }

}