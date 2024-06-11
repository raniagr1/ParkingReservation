package com.example.myapplication.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.common.Destination
import com.example.myapplication.presentation.ui.theme.Pink40
import com.example.myapplication.presentation.ui.theme.Purple80
import com.example.myapplication.presentation.ui.theme.PurpleGrey40
import com.example.myapplication.presentation.viewmodels.ReservationsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import com.example.myapplication.presentation.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyReservationsScreen(
    userViewModel: UserViewModel,
    reservationsViewModel: ReservationsViewModel,
    navController: NavHostController,
    isSignedIn: Boolean, onLogout: () -> Unit
) {
    val isLoggedIn by userViewModel.isLoggedIn.collectAsState()

    if (isLoggedIn ) {
    var dateState by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(0.91F)
    ) {
        IconButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select Date",
                tint = Color.Green, // Change color as needed
                modifier = Modifier.size(24.dp) // Adjust size as needed
            )
        }
        // Notification button
        CustomButton(
            onClick = {onLogout
                userViewModel.logout()
                navController.navigate(Destination.Login.route)}, // Use the provided lambda for logout action
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            backgroundColor = Color(android.graphics.Color.RED),
            contentColor = Color.White

        ) {
            Text("Logout", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val selectedDate = Calendar.getInstance().apply {
                                timeInMillis = datePickerState.selectedDateMillis!!
                            }
                            dateState = dateFormatter.format(selectedDate.time)
                            Toast.makeText(
                                context,
                                "Selected date ${dateFormatter.format(selectedDate.time)} saved",
                                Toast.LENGTH_SHORT
                            ).show()
                            showDatePicker = false
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

        Spacer(modifier = Modifier.height(16.dp))

        val reservationsState = reservationsViewModel.allReservations.collectAsState()
        val reservations = reservationsState.value

        LazyColumn {
            items(reservations) { reservation ->
                if (dateState.isEmpty() ||  reservation.date.toString() == dateState) {
                    ReservationItem(reservation  , navController)

                }
            }
        }
    }
    }else {
        // User is not logged in, show login screen
        // You can replace this with your actual login screen composable
        LoginPage(navController, userViewModel,isSignedIn)
    }
}