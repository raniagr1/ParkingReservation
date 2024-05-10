package com.example.myapplication.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.Destination
import com.example.myapplication.data.model.Parking
import com.example.myapplication.ui.viewmodels.ParkingViewModel
import com.example.trying.R

@Composable

fun DisplayDetail(parkingId: Int, parkingViewModel: ParkingViewModel?, navController: NavHostController) {
    var parking by remember { mutableStateOf<Parking?>(null) }

    LaunchedEffect(key1 = parkingId) {
        if (parkingViewModel != null) {
            parkingViewModel.getParkingById(parkingId)
        }
    }


    parking = parkingViewModel?._parking?.value

    parking?.let { park ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = R.drawable.car2),
                    contentDescription = "Tomato Image",
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(44.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                    .background(Color.Blue)
                    .padding(24.dp)
            )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = park.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = park.rating.toString(),
                        color = Color.DarkGray,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = park.description,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(28.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = "Price per place",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                        Text(
                            text = park.pricePerHour.toString(),
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue
                    ),
                    onClick = {navController.navigate(Destination.Reservation.route)  }) {
                    Text(
                        text = "Book a place",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    } ?: run {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Liste des parkings $parkingId",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}