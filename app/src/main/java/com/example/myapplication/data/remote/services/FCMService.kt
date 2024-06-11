package com.example.myapplication.data.remote.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.app.MainActivity
import com.example.myapplication.app.MyApplication
import com.example.myapplication.common.TokenInfo
import com.example.trying.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCMService", "New token: $token")
        // Send the new token to your server to update the user's token
        sendTokenToServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Retrieve reservationId from the message data
        val reservationId = message.data["reservationId"]

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = reservationId?.toInt() ?: 0  // Unique notification ID

        val channelId = "Firebase_Messaging_ID"
        val channelName = "Firebase Messaging"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
                    description = "Channel for reservation notifications"
                }
            )
        }

        // Create a deep link intent with reservationId
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("myapp://reservationdetails/$reservationId"), // Handle by Compose navigation
            this,
            MainActivity::class.java // MainActivity is the entry point
        )

        // Add flags to avoid creating multiple instances of MainActivity
        deepLinkIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // Set pending intent flag based on Android version
        val pendingIntentFlag = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            0
        } else {
            PendingIntent.FLAG_IMMUTABLE
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId,
            deepLinkIntent,
            pendingIntentFlag
        )

        // Customize the notification
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.notification?.title ?: "Reservation Reminder")
            .setContentText(message.notification?.body ?: "Your reservation is coming up tomorrow.")
            .setSmallIcon(R.drawable.car2) // Replace with your custom notification icon
            .setColor(ContextCompat.getColor(this, R.color.purple_200)) // Customize notification color
            .setAutoCancel(true) // Notification disappears when tapped
            .setContentIntent(pendingIntent) // Set the intent to open the app
            .setStyle(NotificationCompat.BigTextStyle().bigText(message.notification?.body)) // Expandable text
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Set notification priority
            .build()

        notificationManager.notify(notificationId, notification)

}

    //can be sent to the server
 /*   private suspend fun saveGCMToken(token: String){
        val gckTokenKey = stringPreferencesKey("gcm_token")
        baseContext.dataStore.edit {  pref ->
            pref[gckTokenKey]= token
        }
    }*/
    private fun sendTokenToServer(token: String) {
        // Construct the request data
        val tokenInfo = TokenInfo(token, userId = 1) // Replace `userId` with actual user ID if available

        // Make a network request to send the token to your server
        val endpoint = (application as MyApplication).endpoint

        // Use Retrofit to send the token
        endpoint.saveToken(tokenInfo).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("FCM", "Token sent to server successfully")
                } else {
                    Log.e("FCM", "Failed to send token to server: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("FCM", "Error sending token to server", t)
            }
        })
    }

}