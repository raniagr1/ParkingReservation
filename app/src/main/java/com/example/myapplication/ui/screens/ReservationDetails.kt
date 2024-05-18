package com.example.myapplication.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Reservation
import com.example.myapplication.ui.viewmodels.ReservationsViewModel
import java.text.SimpleDateFormat

@Composable
fun ReservationDetails(reservationId: Int, reservationViewModel: ReservationsViewModel?) {
    var reservation by remember {
        mutableStateOf<Reservation?>(null)
    }

    LaunchedEffect(Unit) {
        reservationViewModel?.let { viewModel ->
            var result = viewModel.getReservationsById(reservationId)
            reservation = result
        }
    }

    reservation?.let { reservation ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Reservation ID: ${reservation.reservationId}")
            Text("Place ID: ${reservation.parkId}")
            Text("User ID: ${reservation.userId}")
            Text("Date: ${SimpleDateFormat("yyyy-MM-dd").format(reservation.date)}")
            Text("Entry Time: ${reservation.entryTime}")
            Text("Exit Time: ${reservation.exitTime}")
            Text("Payment Validated: ${if (reservation.paymentValidated) "Yes" else "No"}")
        }
    }
}
