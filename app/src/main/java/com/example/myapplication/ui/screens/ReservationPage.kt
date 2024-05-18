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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
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

fun main() {
  println(convertStringToDate("2024-05-05"))
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationBookingScreen(parkingId:Int, reservationVM: ReservationsViewModel, parkingViewModel: ParkingViewModel?)
    {
        var reservationId:Long=0;
        // State for form inputs
        val dateState = remember { mutableStateOf("") }
        val entryTimeState = remember { mutableStateOf("") }
        val exitTimeState = remember { mutableStateOf("") }
        val ConfirmationState = remember { mutableStateOf(false) }
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

        // val dateFormatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }

        LaunchedEffect(key1 = parkingId) {
            if (parkingViewModel != null) {
                parkingViewModel.getParkingById(parkingId)
            }
        }


        parking = parkingViewModel?._parking?.value
        val context = LocalContext.current
        /*  LaunchedEffect(paymentValidatedState.value) {
              if (paymentValidatedState.value) {
                  val reservation = Reservation(
                      placeId = 1, // Replace with actual place ID
                      userId = 1, // Replace with actual user ID
                      date = SimpleDateFormat("yyyy-MM-dd").parse(dateState.value),
                      entryTime = entryTimeState.value,
                      exitTime = exitTimeState.value,
                      paymentValidated = true // Payment is already validated
                  )
                  // Insert reservation into database
                  viewModel.insert(reservation)
              }
          }*/
        Column(
            modifier = Modifier
                .padding(16.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Make Reservation for", color = Color(0xFFADD8E6), fontSize = 22.sp)
            Column(

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                    ,

                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally,
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
                        containerColor = Color(0xFFADD8E6)
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
             if(dateState.value !=""){Text(text = "Selected Date: ${convertStringToDate(dateState.value)}")}
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
                        containerColor = Color(0xFFADD8E6)
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
                        containerColor = Color(0xFFADD8E6)
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
                val calculatedPrice = calculateReservationPrice(entryTimeState.value,exitTimeState.value,15.0)
                Spacer(modifier = Modifier.height(7.dp))
                Text(text ="The total for park ${parking!!.id} price is ${calculatedPrice} DA" , color = Color(0xFFADD8E6))
                Button(
                    onClick = {
                        if (( dateState.value !="")&&( entryTimeState.value !="")&&( exitTimeState.value !="")) {
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
                        }else{
                            val missing = mutableListOf<String>()
                            if (dateState.value.isEmpty()) missing.add("Date")
                            if (entryTimeState.value.isEmpty()) missing.add("Entry Time")
                            if (exitTimeState.value.isEmpty()) missing.add("Exit Time")
                            missingFields.value = "Missing fields: ${missing.joinToString(", ")}"

                            Toast
                                .makeText(context,  missingFields.value , Toast.LENGTH_SHORT)
                                .show()
                        }

                              },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Check Available Places")
                }
                if (reservationVM.reservationStatus != null) {
                    var txt =
                    Text(
                        text = reservationVM.reservationMessage.value ?: "",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()


                    )
                }
                // Display available places
              if ( showAvailablePlaces.value && dateState.value !="") {
                  Text("Available Places: ${reservationVM.availablePlacesState.value}")
              }
if(reservationVM.availablePlacesState.value>0) {
    Button(
        onClick = {
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
            reservationVM.createReservation(reservation)
            // Insert reservation into database

            /*rese.viewModelScope.launch {
                /*  val user = User(
                      firstName = "John",
                      lastName = "Doe",
                      userName = "johndoe",
                      passwrd = "password"
                  )

                  val parking = OParking(
                      name = "Example Parking",
                      commune = "Example Commune",
                      imageResId = R.drawable.car2,
                      capacity = 100,
                      pricePerHour = 10.0,
                      description = "Example Description",
                      rating = 4.5f,
                      longitude = 0.0f,
                      latitude = 0.0f
                  )

                  val place = Place(
                      number = 1,
                      floorId = 1 // Assuming you have a floor with id 1
                  )

                  val floor = Floor(
                      number = 1,
                      parkingId = 1, // Assuming you have a parking with id 1
                      nbPlaces = 50
                  )*/


                reservationId = viewModel.insert(reservation)
                // After inserting into database, set paymentValidatedState to true
                paymentValidatedState.value = true
            }*/

        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Validate Payment")
    }
}
                if (paymentValidatedState.value) {

                    ReservationConfirmation(reservation = Reservation(

                        parkId = parking?.id!!,
                        userId = 1, // Replace with actual user ID
                        date = SimpleDateFormat("yyyy-MM-dd").parse(dateState.value),
                        entryTime = entryTimeState.value,
                        exitTime = exitTimeState.value,
                         paymentValidated = true // Payment is already validated
                    ).apply {
                        dateString = dateState.value
                    },reservationId)
                }

            }
        }}


    private fun calculateReservationPrice(entryTime: String, exitTime: String,Price:Double): Double {
        if (entryTime.length>0 && entryTime.length>0) {
            val entryHour = entryTime.split(":")[0].toDouble()
            val exitHour = exitTime.split(":")[0].toDouble()
            val entryMinute = entryTime.split(":")[1].toDouble()
            val exitMinute = exitTime.split(":")[1].toDouble()
            var hours = exitHour - entryHour
            if (hours < 0) {
                hours = 1.0;
            }
            if (entryMinute > exitMinute) {
                hours -= 1;
            }
            return Price * hours
        }else{
            return  0.0;
        }

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

/*    @Composable
    fun ReservationConfirmationScreen(navController: NavHostController, reservationId: Int,viewModel: ReservationsViewModel) {
        // Fetch reservation details based on reservationId
        var reservation by remember {
            mutableStateOf<Reservation?>(null)
        }

        LaunchedEffect(Unit) {
            viewModel?.let { viewModel ->
                var result = viewModel.getReservationsById(reservationId)
                reservation = result
            }
        }



        // Show confirmation details
        reservation?.let { ReservationConfirmation(reservation = it, reservation!!.reservationId.toLong()) }

        // Show QR code
        val qrCodeData = "Reservation ID: $reservationId"
        /* val qrCodePainter = generateQRCode(qrCodeData)
         Image(
             painter = qrCodePainter,
             contentDescription = "QR Code",
             modifier = Modifier
                 .fillMaxSize()
                 .padding(16.dp)
         )
     */
        // Example of navigating back to the previous screen
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Back")
        }
    }*/
