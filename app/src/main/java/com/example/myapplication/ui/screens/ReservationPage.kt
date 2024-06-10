package com.example.myapplication.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import com.example.myapplication.CheckAvailablePlacesRequest
import com.example.myapplication.data.model.Reservation
import com.example.myapplication.ui.viewmodels.ReservationsViewModel


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.myapplication.Destination
import com.example.myapplication.URL
import com.example.myapplication.data.model.Parking
import com.example.myapplication.ui.theme.Pink40
import com.example.myapplication.ui.theme.Purple80
import com.example.myapplication.ui.theme.PurpleGrey40
import com.example.myapplication.ui.viewmodels.ParkingViewModel

import com.example.trying.R

import kotlinx.coroutines.launch

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

import java.util.Calendar
import java.util.Date

import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes


fun convertStringToDate(dateString: String): Date {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    return inputFormat.parse(dateString)

}



@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationBookingScreen(parkingId:Int, reservationVM: ReservationsViewModel, parkingViewModel: ParkingViewModel?,navController: NavHostController)
    {
        val reservationId = remember { mutableStateOf<Int?>(0) }
        // State for form inputs
        val dateState = remember { mutableStateOf("") }
        val entryTimeState = remember { mutableStateOf("") }
        val exitTimeState = remember { mutableStateOf("") }
        val ConfirmationState = remember { mutableStateOf(false) }
        val placeNum = remember { mutableStateOf<Int?>(0) }


        val paymentValidatedState = remember { mutableStateOf(false) }
        var showDatePicker by remember { mutableStateOf(false) }
        var showEntryTimePicker by remember { mutableStateOf(false) }
        var showExitTimePicker by remember { mutableStateOf(false) }
        val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd") }
        val showAvailablePlaces = remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState()
        val entryTimePickerState = rememberTimePickerState()
        val exitTimePickerState = rememberTimePickerState()
        var parking by remember { mutableStateOf<Parking?>(null) }
        val missingFields = remember { mutableStateOf("") }
        val context = LocalContext.current
        // val dateFormatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }

        LaunchedEffect(key1 = parkingId) {
            if (parkingViewModel != null) {
                parkingViewModel.getParkingById(parkingId)
            }
        }


        parking = parkingViewModel?._parking?.value



        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Make a Reservation ", color = Color(0xFF0087de), fontSize = 22.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp))
            Column(

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                  //  elevation =
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Park name: ${parking?.name}")
                        Text(text = "Park location: ${parking?.commune}")
                        Text(text = "Park price: : ${parking?.pricePerHour}")
                    }}}


            Column(
                modifier = Modifier
                    .padding(16.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Calendar input for selecting reservation date
                Button(
                    onClick = {
                        showDatePicker = true // Show the Date Picker dialog
                        showAvailablePlaces.value=false
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(55.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0087de)
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Hour Icon",
                        tint = Color.Black, // Change color as needed
                        modifier = Modifier.size(24.dp) // Adjust size as needed
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Select The date", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))//
                Text(text = "Selected Date: ${dateState.value}") // Display selected date
           //     Text(text = "Selected Date: ${SimpleDateFormat("yyyy-MM-dd").parse(dateState.value)}") // Display selected date
                Spacer(modifier = Modifier.height(4.dp))
                // Input for entry time
                Button(
                    onClick = {
                        showEntryTimePicker = true // Show the Entry Time Picker dialog
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(55.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0087de)
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Hour Icon",
                        tint = Color.Green, // Change color as needed
                        modifier = Modifier.size(24.dp) // Adjust size as needed
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Select the Entry Time ", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Selected Entry Time: ${entryTimeState.value}") // Display selected entry time
                Spacer(modifier = Modifier.height(4.dp))
                // Input for exit time
                Button(
                    onClick = {
                        showExitTimePicker = true // Show the Exit Time Picker dialog
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(55.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0087de)
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Hour Icon",
                        tint = Color.Red, // Change color as needed
                        modifier = Modifier.size(24.dp) // Adjust size as needed
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Select the Exit Time", fontSize = 18.sp)
                }
                Text(text = "Selected Exit Time: ${exitTimeState.value}") // Display selected exit time

                // Date Picker dialog
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    val selectedDate = Calendar.getInstance().apply {
                                        timeInMillis = datePickerState.selectedDateMillis!!
                                    }
                                    dateState.value= dateFormatter.format(selectedDate.time)
                                    if (selectedDate.after(Calendar.getInstance())) {
                                        Toast.makeText(
                                            context,
                                            "Selected date ${dateFormatter.format(selectedDate.time)} saved",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        showDatePicker = false
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Selected date should be after today, please select again",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            ) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    // Handle dismissal action
                                    showDatePicker = false // Dismiss dialog
                                }
                            ) { Text("Cancel") }
                        }
                    ) {
                        DatePicker(
                            state = datePickerState,
                            colors = DatePickerDefaults.colors(
                                todayContentColor = Pink40,
                                todayDateBorderColor = Purple80,
                                selectedDayContentColor = Purple80,
                                dayContentColor = Purple80,
                                selectedDayContainerColor = PurpleGrey40,
                            )
                        )
                    }
                }

                // Entry Time Picker dialog
                if (showEntryTimePicker) {
                    TimePickerDialog(
                        onDismissRequest = { showEntryTimePicker = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    entryTimeState.value = entryTimePickerState.hour.toString().padStart(2, '0')+":"+entryTimePickerState.minute.toString().padStart(2, '0') // Update entry time state

                                    // Handle confirmation action
                                    showEntryTimePicker = false // Dismiss dialog
                                }
                            ) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    // Handle dismissal action
                                    showEntryTimePicker = false // Dismiss dialog
                                }
                            ) { Text("Cancel") }
                        }
                    ) {
                        TimePicker(
                            state = entryTimePickerState,
                            colors = TimePickerDefaults.colors(
                                // Customize colors if needed
                            )
                        )
                    }
                }

                // Exit Time Picker dialog
                // Exit Time Picker dialog
                if (showExitTimePicker) {
                    TimePickerDialog(
                        onDismissRequest = { showExitTimePicker = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    // Handle confirmation action
                                    exitTimeState.value =exitTimePickerState.hour.toString().padStart(2, '0')+":"+exitTimePickerState.minute.toString().padStart(2, '0')
                                    showExitTimePicker = false // Dismiss dialog
                                }
                            ) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    // Handle dismissal action
                                    showExitTimePicker = false // Dismiss dialog
                                }
                            ) { Text("Cancel") }
                        }
                    ) {
                        TimePicker(
                            state = exitTimePickerState,
                            colors = TimePickerDefaults.colors(
                                // Customize colors if needed
                            )
                        )
                    }
                }
                val calculatedPrice = parking?.let {
                    calculateReservationPrice(entryTimeState.value,exitTimeState.value,
                        it.pricePerHour)
                }
                Spacer(modifier = Modifier.height(7.dp))
                Text(text ="The total for park ${parking!!.id} price is ${calculatedPrice} DA" , color = Color(0xFFADD8E6))
                if (dateState.value.isNotEmpty() && entryTimeState.value.isNotEmpty() && exitTimeState.value.isNotEmpty()) {
                    if (!isValidReservationTime(entryTimeState.value, exitTimeState.value)) {
                        Text(
                            text = "Exit time must be after entry time.",
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    } else {   Button(
                        onClick = {


                                paymentValidatedState.value = true // Set payment validated state to true
                            val formattedDate = convertStringToDate(dateState.value)

                            // Assuming all required fields are filled
                            val reservation = Reservation(
                                parkId = parking?.id!!,
                                userId = 1,
                                date = formattedDate,
                                entryTime = entryTimeState.value,
                                exitTime = exitTimeState.value,
                                paymentValidated = true ,// Payment is already validated
                                imgUrl =parking?.image!! ,
                                parkName = parking?.name!!,
                                totalPrice = calculatedPrice!!,
                            ).apply {
                                dateString = dateState.value
                            }
                         //   val reservationsState =reservationVM.reservationNum.collectAsState()

                          reservationVM.insertReservation(reservation)
                            placeNum.value= reservationVM.placeNum.value
                          reservationId.value =  reservationVM.reservationNum.value
                            }

                        ,

                        modifier = Modifier.fillMaxWidth(0.8f).height(55.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFADD8E6)
                        ),
                    ) {
                        Text(text = "Validate Payment", fontSize = 18.sp)
                    }
                }}

                if (paymentValidatedState.value) {
                    Toast.makeText(
                        context,
                        "Reservation Number: ${reservationId.value} and Place Number: ${placeNum.value} ",
                        Toast.LENGTH_SHORT
                    ).show()

                    AlertDialog(
                        onDismissRequest = { /* Do nothing */ },

                        text = {
                            Column {
                                reservationId.value?.let {
                                    ParkingConfirmationScreen(parking!!.name?:"",it,placeNum.value?:1,
                                        entryTimeState.value,exitTimeState.value)
                                }
                                /*Text("Reservation Number: ${reservationId.value}")
                                Text("Place Number: ${placeNum.value}")
                                Text("Parking Place: ${parking?.name}")
                                QrCodePreview(data = "Reservation Number: ${reservationId.value}, Place Number: ${placeNum.value}")
*/
                            }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    navController.navigate(Destination.List.route)
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }


           /*     Button(
                    onClick = {
                        paymentValidatedState.value = false // Reset payment validated state

                            val formattedDate = convertStringToDate(dateState.value)

                            // Assuming all required fields are filled
                            val reservation = Reservation(
                                parkId = parking?.id!!,
                                userId = 1,
                                date = formattedDate,
                                entryTime = entryTimeState.value,
                                exitTime = exitTimeState.value,
                                  paymentValidated = true // Payment is already validated
                            ).apply {
                                dateString = dateState.value
                            }

                            reservationVM.insertReservation(reservation)


                              },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Book a place")
                }*/



            }
        }}


private fun calculateReservationPrice(entryTime: String, exitTime: String, pricePerHour: Double): Double {
    if (entryTime.isNotEmpty() && exitTime.isNotEmpty()) {
        val entryParts = entryTime.split(":").map { it.toDouble() }
        val exitParts = exitTime.split(":").map { it.toDouble() }
        val entryHour = entryParts[0] + entryParts[1] / 60
        val exitHour = exitParts[0] + exitParts[1] / 60
        var hours = exitHour - entryHour
        if (hours < 0) hours = 1.0 // Ensure at least 1 hour if negative (overnight stay)
        return pricePerHour * hours
    }
    return 0.0
}
fun isValidReservationTime(entryTime: String, exitTime: String): Boolean {
    val entryTimeParts = entryTime.split(":").map { it.toInt() }
    val exitTimeParts = exitTime.split(":").map { it.toInt() }

    val entryCalendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, entryTimeParts[0])
        set(Calendar.MINUTE, entryTimeParts[1])
    }

    val exitCalendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, exitTimeParts[0])
        set(Calendar.MINUTE, exitTimeParts[1])
    }

    return exitCalendar.after(entryCalendar)
}



    // Placeholder function for generating QR code
    /*
    @Composable
    private fun QRCodeGenerator(reservation: Reservation) {
        // Generate QR code using reservation data
    }
    */
    @Composable
    fun ReservationConfirmation(reservation: Reservation,idreserv:Long) {
        // Composable to display confirmation message with reservation details and QR code
        // You can use this composable to display confirmation details and QR code
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Reservation confirmed!")
            Text("Parking ID: ${reservation.parkId}")
            // Display QR code here
        }
    }
    @Composable
    fun TimePickerDialog(
        title: String = "Select Time",
        onDismissRequest: () -> Unit,
        confirmButton: @Composable (() -> Unit),
        dismissButton: @Composable (() -> Unit)? = null,
        containerColor: Color = MaterialTheme.colorScheme.surface,
        content: @Composable () -> Unit,
    ) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min)
                    .background(
                        shape = MaterialTheme.shapes.extraLarge,
                        color = containerColor
                    ),
                color = containerColor
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        text = title,
                        style = MaterialTheme.typography.labelMedium
                    )
                    content()
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        dismissButton?.invoke()
                        confirmButton()
                    }
                }
            }
        }
    }

@Composable
fun ParkingConfirmationScreen(parkingName:String,reservationNum:Int,placeNum:Int,entryTime: String,exitTime: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
       ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Parking Icon
            Image(
                painter = painterResource(id = R.drawable.car2), // Replace with actual icon
                contentDescription = "Parking Icon",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00BFA5)), // Assuming it's a teal background
            )

            Spacer(modifier = Modifier.height(16.dp))

            // "You booked it!" Text
            Text(
                text = "You booked it!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black

            )

            Spacer(modifier = Modifier.height(16.dp))

            // Parking Slot and QR Code
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Parking Name",

                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray

                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = parkingName,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black

                )
                Spacer(modifier = Modifier.height(8.dp))
                QrCodePreview("Reservation number: ")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Reservation number",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray

                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = reservationNum.toString(),

                        fontWeight = FontWeight.Bold,
                        color = Color.Black

                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Vehicle Info
            Text(
                text = "Place number",

                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray

            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = placeNum.toString(),

                    fontWeight = FontWeight.Bold,
                    color = Color.Black

            )

            Spacer(modifier = Modifier.height(16.dp))

            // Enter and Exit Time
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Enter After",

                            color = Color.Gray

                    )
                    Text(
                        text = "Exit Before",

                            color = Color.Gray

                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = entryTime,

                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = exitTime,

                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ,
                        textAlign = TextAlign.End
                    )
                }
            }



        }
    }
}