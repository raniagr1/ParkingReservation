package com.example.myapplication.presentation.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Reservation
import com.example.myapplication.presentation.viewmodels.ReservationsViewModel
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
           /*
            Text("Reservation number: ${reservation.reservationId}")
            Text("Place number: ${reservation.placeNum}")
           // Text("User ID: ${reservation.userId}")
            Text("Date: ${SimpleDateFormat("yyyy-MM-dd").format(reservation.date)}")
            Row {
                Column {
                    Text("Entry Time:")
                    Text("${reservation.entryTime}")
                }
                Column {
                    Text("Exit Time:")
                    Text("${reservation.exitTime}")
                }

            }

            Text("Payment Validated: ${if (reservation.paymentValidated) "Yes" else "No"}")
*/
            ReservationDetailsScreen(reservation.parkName?:"",SimpleDateFormat("yyyy-MM-dd").format(reservation.date),reservation.reservationId,reservation.placeNum?:0,reservation.entryTime,reservation.exitTime,reservation.totalPrice.toString())
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationDetailsScreen(parkName:String,date:String,reservNum:Int,placeNum:Int,entryTime:String,exitTime:String,total:String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reservation Details", color = Color.White) },


            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Journey Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Parking: ${parkName}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Date: ${date}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bus Information Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    InfoRow(label = "Reservation number", value = reservNum.toString())
                    InfoRow(label = "Place Number", value = placeNum.toString())
                    InfoRow(label = "Arrival", value = entryTime)
                    InfoRow(label = "Exit", value = exitTime)
                    InfoRow(label = "Payment", value = "Validated")

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Total Cost
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total:",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$total DA",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp)

            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    QrCodePreview("Reservation number: ${reservNum}")
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Note Section
            Text(
                text = "Just show your QR code when entering the park",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))


        }
    }
}

@Composable
fun InfoRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Medium,
            color = if (isBold) Color.Black else Color.Gray,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}