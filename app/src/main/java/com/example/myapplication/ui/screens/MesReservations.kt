package com.example.myapplication.ui.screens

import android.graphics.Color.RED
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.Destination
import com.example.myapplication.MainActivity
//import com.example.myapplication.data.model.Reservation
import com.example.myapplication.ui.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MyReservationsScreen(userViewModel: UserViewModel,navController: NavHostController,isSignedIn: Boolean,onLogout: () -> Unit // Define a lambda parameter for logout actio
) {
// Access isLoggedIn state from UserViewModel
    val isLoggedIn by userViewModel.isLoggedIn.collectAsState()

    if (isLoggedIn ) {
        val dateState = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button
                IconButton(
                    onClick = {
                        // Handle back button action
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary// Sky blue color
                    )
                }
                Text(
                    text = "Welcome User!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White, // White color
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                // Notification button
                CustomButton(
                    onClick = {onLogout
                        navController.navigate(Destination.MyReservations.route)}, // Use the provided lambda for logout action
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    backgroundColor = Color(RED),
                    contentColor = Color.White

                ) {
                    Text("Logout", color = Color.White)
                }
            }
            OutlinedTextField(
                value = dateState.value,
                onValueChange = { dateState.value = it },
                label = { Text("Enter Date (YYYY-MM-DD)") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //val reservationsState = reservationsViewModel.allReservations.collectAsState()
            //val reservations = reservationsState.value

            for (i in 1..7) {
                ReservationCard(i)
            }


        }
    }else {
        // User is not logged in, show login screen
        // You can replace this with your actual login screen composable
        LoginPage(navController, userViewModel,isSignedIn)
    }
}



@Composable
fun ReservationCard(reservation: Int) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            // .clickable(onClick = onClick)
            .fillMaxWidth(),

        ) {
        // Display reservation details
        // You can customize this according to your reservation data structure
        Text(text = "Reservation ID: $reservation ")
        // Display other reservation details as needed
    }
}
