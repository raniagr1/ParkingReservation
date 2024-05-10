package com.example.trying.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.trying.data.model.Reservation
import com.example.trying.ui.viewmodels.ReservationsViewModel

@Composable
fun MyReservationsScreen(
    reservationsViewModel: ReservationsViewModel
) {
    val dateState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = dateState.value,
            onValueChange = { dateState.value = it },
            label = { Text("Enter Date (YYYY-MM-DD)") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val reservationsState = reservationsViewModel.allReservations.collectAsState()
        val reservations = reservationsState.value

        LazyColumn {
            items(reservations) { reservation ->
                if (dateState.value.isEmpty() ||  reservation.date.toString() == dateState.value) {
                    ReservationCard(reservation) {
                       // navigateToReservationDetails(reservation)
                    }
                }
            }
        }
    }
}



@Composable
fun ReservationCard(reservation: Reservation, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
            .fillMaxWidth(),

    ) {
        // Display reservation details
        // You can customize this according to your reservation data structure
        Text(text = "Reservation ID: ${reservation.reservationId}")
        // Display other reservation details as needed
    }
}
