package com.example.trying.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trying.data.model.Reservation
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ReservationBookingScreen() {
    // State for form inputs
    val dateState = remember { mutableStateOf("") }
    val entryTimeState = remember { mutableStateOf("") }
    val exitTimeState = remember { mutableStateOf("") }
    val paymentValidatedState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Calendar input for selecting reservation date
        OutlinedTextField(
            value = dateState.value,
            onValueChange = { dateState.value = it },
            label = { Text("Select Date") },
            modifier = Modifier
                .fillMaxWidth()
        )

        // Input for entry time
        OutlinedTextField(
            value = entryTimeState.value,
            onValueChange = { entryTimeState.value = it },
            label = { Text("Enter Entry Time") },
            modifier = Modifier
                .fillMaxWidth()
        )

        // Input for exit time
        OutlinedTextField(
            value = exitTimeState.value,
            onValueChange = { exitTimeState.value = it },
            label = { Text("Enter Exit Time") },
            modifier = Modifier
                .fillMaxWidth()
        )

        // Calculate reservation price based on selected times
     //   val reservationPrice = calculateReservationPrice(entryTimeState.value, exitTimeState.value)

        // Display reservation price
       // Text("Reservation Price: \$${String.format("%.2f", reservationPrice)}")

        // Button to validate payment
        Button(
            onClick = { paymentValidatedState.value = true },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Validate Payment")
        }

        // Confirmation message with reservation details and QR code after payment validation
      /*  if (paymentValidatedState.value) {
            val reservation = createReservation(dateState.value, entryTimeState.value, exitTimeState.value)
            ConfirmationMessage(reservation)
            // Generate and display QR code
            // Example: QRCodeGenerator(reservation)
        }*/
    }
}

// Function to calculate reservation price based on entry and exit times
/*
private fun calculateReservationPrice(entryTime: String, exitTime: String): Double {
    // Example calculation: $15 per hour
    val entryHour = entryTime.split(":")[0].toDouble()
    val exitHour = exitTime.split(":")[0].toDouble()
    val hours = exitHour - entryHour
    return 15.0 * hours
}

// Function to create a reservation object
private fun createReservation(date: String, entryTime: String, exitTime: String): Reservation {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    val parsedDate: Date? = dateFormatter.parse(date)
    return Reservation(
        placeId = 1, // Replace with actual place ID
        userId = 1, // Replace with actual user ID
        date = parsedDate ?: Date(), // Use parsedDate if not null, otherwise use a default value
        entryTime = entryTime,
        exitTime = exitTime,
        paymentValidated = true // Payment is already validated
    )
}

@Composable
private fun ConfirmationMessage(reservation: Reservation) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reservation confirmed!")
        Text("Reservation ID: ${reservation.reservationId}")
        Text("Place ID: ${reservation.placeId}")
        // Display QR code here
    }
}
*/
// Placeholder function for generating QR code
/*
@Composable
private fun QRCodeGenerator(reservation: Reservation) {
    // Generate QR code using reservation data
}
*/
